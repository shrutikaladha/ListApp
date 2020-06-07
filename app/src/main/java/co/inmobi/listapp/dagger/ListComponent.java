package co.inmobi.listapp.dagger;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent
public interface ListComponent {

    @Subcomponent.Factory
    interface Factory {
        ListComponent create();
    }

}
