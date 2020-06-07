package co.inmobi.listapp.dagger;

import co.inmobi.listapp.ui.list.ListActivity;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent
public interface ListComponent {

    @Subcomponent.Factory
    interface Factory {
        ListComponent create();
    }

    void inject(ListActivity listActivity);
}
