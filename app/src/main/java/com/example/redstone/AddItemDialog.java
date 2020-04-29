package com.example.redstone;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class AddItemDialog extends DialogFragment {

    public final static String BUNDLE = "Bundle";
    ProductInfo productInfo = null;
    AlertDialog dialogWindow;

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

        if (getArguments() != null) {
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
                else if (productInfo != null) {
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

        dialogWindow = builder.create();

        // чтобы работать с цветом - устанавливаем слушателя на момент отрисовки
        dialogWindow.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                // ловим окно
                if (dialogWindow.getWindow() != null)
                    // красим фон в цвет
                    dialogWindow.getWindow().setBackgroundDrawableResource(R.color.dialog);
                // достаем кнопку
                Button b = dialogWindow.getButton(DialogInterface.BUTTON_POSITIVE);
                // красим кнопку в цвет
                b.setBackgroundColor(getResources().getColor(R.color.orangeMain));
            }
        });
        return dialogWindow;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
