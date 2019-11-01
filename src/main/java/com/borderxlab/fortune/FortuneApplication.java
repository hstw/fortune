package com.borderxlab.fortune;

import org.skife.jdbi.v2.DBI;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.borderxlab.fortune.resources.FortuneResource;
import com.borderxlab.fortune.resources.FortunesResource;
import com.borderxlab.fortune.service.FortunePool;
import com.borderxlab.fortune.db.FortuneDAO;


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
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi =
                factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        final FortuneDAO fortuneDAO = jdbi.onDemand(FortuneDAO.class);
        final FortunePool fortunePool = new FortunePool(fortuneDAO);

        environment.jersey().register(new FortuneResource(fortunePool));
        environment.jersey().register(new FortunesResource(fortunePool));

    }
}
