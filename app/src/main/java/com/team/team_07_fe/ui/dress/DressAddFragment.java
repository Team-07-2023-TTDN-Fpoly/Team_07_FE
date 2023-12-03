package com.team.team_07_fe.ui.dress;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.models.DressType;
import com.team.team_07_fe.request.DressRequest;
import com.team.team_07_fe.ui.dresstype.DressTypeViewModel;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.DressViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DressAddFragment extends Fragment {
    public static final String TAG = DressAddFragment.class.getName();
    private TextInputLayout layout_input_name, layout_input_type, layout_input_color, layout_input_size,
    layout_input_price, layout_input_des;
    private AutoCompleteTextView dropdown_type_dress,dropdown_size;
    private DressTypeViewModel dressTypeViewModel;
    private DressViewModel mViewModel;

    private AppCompatButton btn_add_item;
    private ImageView imageView;
    private LoadingDialog loadingDialog;
    private Uri mUri;

    //Adapter danh sách loại áo cưới
    private ArrayAdapter<DressType> dressTypeArrayAdapter;
    private List<DressType> listDressTypeForDropdown;
    //Lựa chọn loại áo cưới
    private DressType selectDressTypeFromDropdown = null;
    //Adapter cho size áo
    private ArrayAdapter<String> sizeArrayAdapter;
    private final String[] stringsSize = {"S","M","L"};
    private final List<String> listSizeForDropdown = Arrays.asList(stringsSize);
    //Lựa chọn size áo
    private String selectSizeFromDropdown ="";
    private final ActivityResultLauncher<Intent> mActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                if (data == null) {
                                    return;
                                }
                                Uri uri = data.getData();
//                                try {// căn chỉnh ảnh thủ công
//                                    InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
//                                    Bitmap selectedImage = BitmapFactory.decodeStream(inputStream);
//                                    Bitmap resizedImage = Bitmap.createScaledBitmap(selectedImage, 541, 241, true);
//
//                                    // Set the resized image to the ImageView
//                                    imageView.setImageBitmap(resizedImage);
//                                } catch (FileNotFoundException e) {
//                                    e.printStackTrace();
//                                }
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                                    imageView.setImageBitmap(bitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dress_add, container, false);

        mViewModel = new ViewModelProvider(requireActivity()).get(DressViewModel.class);
        dressTypeViewModel = new ViewModelProvider(requireActivity()).get(DressTypeViewModel.class);

        loadingDialog = new LoadingDialog(requireContext());
        listDressTypeForDropdown = new ArrayList<>();

        imageView = view.findViewById(R.id.imageView);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý sự kiện khi ImageView được click
                    choseImgFromGallery();

                }
            });
        }

        mapping(view);
        //mapping
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Lấy tất cả thông tin của loại áo cưới
        dressTypeViewModel.getAllDressType(null);

        //Dropdown list DressType
        dressTypeArrayAdapter = new ArrayAdapter<>(requireContext(),android.R.layout.simple_dropdown_item_1line,listDressTypeForDropdown);
        dropdown_type_dress.setAdapter(dressTypeArrayAdapter);

        dressTypeViewModel.getDressTypeList().observe(getViewLifecycleOwner(),dressTypes -> {
            if(dressTypes!=null){
                listDressTypeForDropdown.clear();
                listDressTypeForDropdown.addAll(dressTypes);
                dressTypeArrayAdapter.notifyDataSetChanged();
            }
        });
        //Dropdown list Size
        sizeArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line,listSizeForDropdown);
        dropdown_size.setAdapter(sizeArrayAdapter);
        //Lựa chọn dressType
        dropdown_type_dress.setOnItemClickListener((adapterView, view1, i, l) -> {
            selectDressTypeFromDropdown = (DressType) dressTypeArrayAdapter.getItem(i);
        });
        //Lựa chọn size
        dropdown_type_dress.setOnItemClickListener((adapterView, view1, i, l) -> {
            selectSizeFromDropdown = sizeArrayAdapter.getItem(i);
        });
        btn_add_item.setOnClickListener(this::handleAddDress);
        imageView.setOnClickListener(view1 -> choseImgFromGallery());
        observeData();
    }

    private void observeData() {
        mViewModel.getDataInput().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                loadingDialog.dismiss();
                NavHostFragment.findNavController(this).popBackStack();
                Toast.makeText(requireContext(), "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
                mViewModel.setDataInput(null);
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                loadingDialog.dismiss();
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                mViewModel.setErrorMessage(null);
            }
        });
    }

    private void handleAddDress(View view){
        String name = layout_input_name.getEditText().getText().toString().trim();
        String type = selectDressTypeFromDropdown.getType_id();
        String price = layout_input_price.getEditText().getText().toString().trim();
        String color = layout_input_color.getEditText().getText().toString().trim();
        String size = selectSizeFromDropdown;
        String des = layout_input_des.getEditText().getText().toString().trim();


        if(validateInput(name,price,color, des)){

        //    DressRequest dressRequest = new DressRequest(image, name, type, color, size, price, des);
            //    confirmAddDress(dressRequest);

        }

    }
    private void confirmAddDress(DressRequest dressRequest){
        mViewModel.addDress(dressRequest);
    }
    private void mapping(View view){
        imageView = view.findViewById(R.id.imageView);

        layout_input_name = view.findViewById(R.id.layout_input_name);
        layout_input_price = view.findViewById(R.id.layout_input_price);
        layout_input_type = view.findViewById(R.id.layout_input_type);
        layout_input_color = view.findViewById(R.id.layout_input_color);
        layout_input_size = view.findViewById(R.id.layout_input_size);
        layout_input_des = view.findViewById(R.id.layout_input_des);
        btn_add_item = view.findViewById(R.id.btn_add_item);
        dropdown_type_dress = view.findViewById(R.id.dropdown_type_dress);
        dropdown_size = view.findViewById(R.id.dressSize);
    }
    private boolean validateInput(String name,String price,
                                  String color, String des){
        boolean isValid = true;

        if(TextUtils.isEmpty(name)){
            layout_input_name.setError("Vui lòng nhập tên áo cưới!");
            isValid = false;
        }else{
            layout_input_name.setError(null);
        }

        if(TextUtils.isEmpty(price) ){
            layout_input_price.setError("Vui lòng nhập giá tiền!");
            isValid = false;
        }else{
            layout_input_price.setError(null);
        }
        if (TextUtils.isEmpty(color)) {
            layout_input_color.setError("Vui lòng nhập màu cho áo cưới!");
            isValid = false;
        }else{
            layout_input_color.setError(null);
        }
        if(TextUtils.isEmpty(des)){
            layout_input_des.setError(null);
        }else  {
            layout_input_des.setError("Vui lòng nhập mô tả cho áo cưới!");
            isValid = false;
        }
        //Kiểm tra loại áo đã chọn chưa
        if(selectDressTypeFromDropdown==null){
            layout_input_type.setError("Vui lòng chọn loại áo cưới!");
            isValid = false;
        }else{
            layout_input_type.setError(null);
        }
        //Kiểm tra size đã chọn chưa
        if(selectSizeFromDropdown.isEmpty()){
            Toast.makeText(requireContext(), "Vui lòng chọn size áo!", Toast.LENGTH_SHORT).show();
            isValid = false;
        }else{
            layout_input_type.setError(null);
        }
        return isValid;
    }
    public void choseImgFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Đã chọn ảnh"));

    }

    // nhận uri khi chọn ảnh từ thư viện

//                    result -> {
//                        if (result.getResultCode() == Activity.RESULT_OK) {
//                            Intent data = result.getData();
//                            if (data == null) {
//                                return;
//                            }
//                            mUri = data.getData();
//                            try {
//                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), mUri);
//                                imageView.setImageBitmap(bitmap);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }

    private void callApiResgister() {

    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)requireActivity()).hiddenBottomBar();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) requireActivity()).showBottomBar();
    }
}