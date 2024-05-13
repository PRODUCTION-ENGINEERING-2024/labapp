package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.HelloWorldService;

@Controller
public class HelloWorldController {

    @Autowired
    private HelloWorldService helloWorldService;

    @Autowired
    MeterRegistry metricsRegistry;

    @GetMapping("/hello-world")
    @ResponseBody
    @Timed(value = "hello.greeting.time", description = "Time taken to return greeting")
    @Counted(value = "hello.greeting.count", description = "Times greeting was returned")
    public Greeting sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return helloWorldService.hello(name);
    }

    @GetMapping("/info")
    @ResponseBody
    @Timed(value = "hello.info.time", description = "Time taken to return info")
    @Counted(value = "hello.info.count", description = "Times info was returned")
    public Greeting info(@RequestParam(name="title", required=false, defaultValue="Overview") String title) throws EntityNotFoundException {
        return helloWorldService.buildGreetingFromInfo(title);
    }

}
