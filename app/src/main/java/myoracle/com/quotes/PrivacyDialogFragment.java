package myoracle.com.quotes;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Midhun on 21-04-2017.
 */

public class PrivacyDialogFragment  extends DialogFragment implements View.OnClickListener{

   private Button dismiss;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.privacy_policy, container, false);
        dismiss = (Button) rootView.findViewById(R.id.privacy_dismiss);
        dismiss.setOnClickListener(this);
        getDialog().setTitle("Simple Dialog");
        return rootView;
    }

    @Override
    public void onClick(View v) {

        dismiss();
    }
}
