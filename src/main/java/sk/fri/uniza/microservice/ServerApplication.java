/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

/**
 *
 * @author P
 */
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.hibernate.SessionFactory;
import java.util.Map;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class ServerApplication extends Application<ServerConfiguration> {

    static SessionFactory buildSessionFactory;

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.configure();

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        buildSessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        new ServerApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<ServerConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/images", "/images"));
        bootstrap.addBundle(new ViewBundle<ServerConfiguration>() {
            @Override
            public Map<String, Map<String, String>> getViewConfiguration(ServerConfiguration configuration) {

                return super.getViewConfiguration(configuration); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    @Override
    public void run(ServerConfiguration configuration,
            Environment environment) {

        final UserViewResource userViewResource = new UserViewResource();
        final UserResource userResource = new UserResource(environment.getValidator());
        final TemplateHealthCheck healthCheck
                = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(AuthFactory.binder(new BasicAuthFactory<>(new UserAuthenticator(), "Auth required", LoginClass.class)));
        environment.jersey().register(userViewResource);
        environment.jersey().register(userResource);

    }

}
