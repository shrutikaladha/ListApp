package co.inmobi.listapp.dagger;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, SubcomponentsModule.class})
public interface ApplicationComponent {

    ListComponent.Factory listComponent();

}
