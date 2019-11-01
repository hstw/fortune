package com.borderxlab.fortune;

import com.borderxlab.fortune.resources.FortuneResource;
import com.borderxlab.fortune.resources.FortunesResource;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class FortuneApplication extends Application<FortuneConfiguration> {
    public static void main(String[] args) throws Exception {
        new FortuneApplication().run(args);
    }


    @Override
    public String getName() {
        return "fortune";
    }

    @Override
    public void initialize(Bootstrap<FortuneConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)));

    }

    @Override
    public void run(FortuneConfiguration configuration, Environment environment) {

        environment.jersey().register(new FortuneResource());
        environment.jersey().register(new FortunesResource());

    }
}
