package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.support.GenericHandler;
import org.springframework.integration.scheduling.PollerMetadata;

import java.util.concurrent.Executors;

@SpringBootApplication
@IntegrationComponentScan
public class IntegrationSampleApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx =
                SpringApplication.run(IntegrationSampleApplication.class, args);

        DoSomethingService doSomethingService = ctx.getBean(DoSomethingService.class);

        ClientClass MMASHB = new ClientClass().setEnable(true).setName("MMASHB");
        ClientClass MMASMKB = new ClientClass().setEnable(true).setName("MMASKB");
        ClientClass uknownClientClass = new ClientClass().setEnable(false).setName("sss");

        doSomethingService.doSomething(new IncomingObject().setClientClass(MMASHB).setArgument("wysyłam sms z użyciem serwisu SMS service"));
        doSomethingService.doSomething(new IncomingObject().setClientClass(uknownClientClass).setArgument("nie wysyłam sms-a"));
        doSomethingService.doSomething(new IncomingObject().setClientClass(MMASMKB).setArgument("wysyłam sms z użyciem serwisu SMS service"));
    }

    @Autowired
    SmsService smsService;


    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedDelay(2).get();
    }

    @Bean
    public IntegrationFlow smsFlow() {
        return IntegrationFlows.from("incomingObject.input")
                .split("payload")
                .channel(MessageChannels.executor(Executors.newCachedThreadPool()))
                .filter((GenericSelector<IncomingObject>) incomingObject -> incomingObject.getClientClass().isEnable())
                .route("payload.clientClass.enable", spec -> spec.channelMapping("true", "enableSmsFlow"))
                .aggregate()
                .get();
    }

    @Bean
    public IntegrationFlow smsEnableFlow() {
        return IntegrationFlows.from(MessageChannels.queue("enableSmsFlow", 10))
                .handle((GenericHandler<IncomingObject>) (payload, headers) -> {
                    System.out.println("wysyłam sms-a");
                    return smsService.send();
                })
                .channel("output")
                .get();
    }

    @Bean
    public IntegrationFlow resultFlow() {
        return IntegrationFlows.from("output")
                .aggregate().get();
    }


}
