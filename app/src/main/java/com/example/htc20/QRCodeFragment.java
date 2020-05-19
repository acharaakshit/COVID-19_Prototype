package com.example.htc20;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QRCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QRCodeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String unique_id;
    private ImageView imageView_entry, imageView_exit;

    public QRCodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment QRCodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QRCodeFragment newInstance(String param1) {
        QRCodeFragment fragment = new QRCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            unique_id = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_q_r_code, container, false);
        imageView_entry = root.findViewById(R.id.img_qrCodeEntry);
        imageView_exit = root.findViewById(R.id.img_qrCodeExit);

        String text_entry = unique_id + ":entry";
        String text_exit = unique_id + ":exit";

        new ImageDownloaderTask(imageView_entry).execute("https://api.qrserver.com/v1/create-qr-code/?size=1000x1000&data=" + text_entry);
        new ImageDownloaderTask(imageView_exit).execute("https://api.qrserver.com/v1/create-qr-code/?size=1000x1000&data=" + text_exit);

        return root;
    }
}
