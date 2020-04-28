package com.example.redstone;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddItemDialog extends DialogFragment {

    public final static String BUNDLE = "Bundle";
    ProductInfo productInfo = null;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_item, null);
        builder.setView(view);

        final EditText productEt = view.findViewById(R.id.productEt),
                weightEt = view.findViewById(R.id.weightEt),
                valueEt = view.findViewById(R.id.valueEt);

        String text = getResources().getString(R.string.add);

        if (getArguments() != null){
            productInfo = (ProductInfo) getArguments().getSerializable(BUNDLE);
            if (productInfo != null) {
                productEt.setText(productInfo.getName());
                weightEt.setText(String.valueOf(productInfo.getWeight()));
                valueEt.setText(String.valueOf(productInfo.getValue()));
                text = getString(R.string.save);
            }
        }

        builder.setPositiveButton(text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null && productInfo == null)
                    mainActivity.addProduct(productEt.getText().toString(),
                            Integer.parseInt(weightEt.getText().toString()),
                            Integer.parseInt(valueEt.getText().toString()));
                else if (productInfo != null){
                    productInfo.setName(productEt.getText().toString());
                    productInfo.setWeight(Integer.parseInt(weightEt.getText().toString()));
                    productInfo.setValue(Integer.parseInt(valueEt.getText().toString()));
                    Toast.makeText(getContext(), R.string.changes_saved, Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().cancel();
            }
        });
        return builder.create();
    }
}
