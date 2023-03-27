package proyingercom.example.points3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class VetMarketDialogFragment extends DialogFragment {

    public static final String ARGUMENTO_TITTLE ="TITLE";

    public static final String ARGUMENTO_FULL_SPINET ="FULL SNIPPET";


    private  String title;
    private  String fullSnippet;

    public static VetMarketDialogFragment newInstance(String title, String fullSnippet){

        VetMarketDialogFragment fragment = new VetMarketDialogFragment();
        Bundle b = new Bundle();

        b.putString(ARGUMENTO_TITTLE,"title");
        b.putString(ARGUMENTO_FULL_SPINET,"fullSnippet");
        fragment.setArguments(b);

        return fragment;

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new AlertDialog.Builder(getActivity()).setTitle(fullSnippet).create();

        return dialog;
    }

}
