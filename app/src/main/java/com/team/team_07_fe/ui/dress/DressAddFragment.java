package com.team.team_07_fe.ui.dress;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.RealPathUtil;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.models.DressType;
import com.team.team_07_fe.request.DressRequest;
import com.team.team_07_fe.ui.dresstype.DressTypeViewModel;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.DressViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;


public class DressAddFragment extends Fragment {
    public static final String TAG = DressAddFragment.class.getName();
    private static final int MY_REQUEST_CODE = 10;
    private static final int SELECT_PICTURE = 1;
    private TextInputLayout layout_input_name, layout_input_type, layout_input_color, layout_input_size,
    layout_input_price, layout_input_des;
    private AutoCompleteTextView dropdown_type_dress,dropdown_size;
    private DressTypeViewModel dressTypeViewModel;
    private DressViewModel mViewModel;

    private AppCompatButton btn_add_item;
    private ImageView imageView;
    private LoadingDialog loadingDialog;
    private Uri mUri = null;

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
    String image = ""; // Chuỗi rỗng ban đầu


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dress_add, container, false);

        mViewModel = new ViewModelProvider(requireActivity()).get(DressViewModel.class);
        dressTypeViewModel = new ViewModelProvider(requireActivity()).get(DressTypeViewModel.class);

        loadingDialog = new LoadingDialog(requireContext());
        listDressTypeForDropdown = new ArrayList<>();


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
        dropdown_size.setOnItemClickListener((adapterView, view1, i, l) -> {
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
        String price  = layout_input_price.getEditText().getText().toString().trim();
        String color = layout_input_color.getEditText().getText().toString().trim();
        String size = selectSizeFromDropdown;
        String des = layout_input_des.getEditText().getText().toString().trim();

        if(validateInput(name,price,color)){
            loadingDialog.show();
            try {
                // Truy xuất dữ liệu từ URI
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(mUri);

                // Lưu dữ liệu vào một tệp mới trong thư mục tạm thời của ứng dụng
                File tempFile = createTempFile();
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
                //Tạo 1 request body để truyền ảnh
                RequestBody requestFile = RequestBody.create(MediaType.parse(requireActivity().getContentResolver().getType(mUri)), image);

                confirmAddDress(requestFile,tempFile,name,selectDressTypeFromDropdown.getType_id(),color,size,Long.parseLong(price),des);

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(requireContext(), "Lỗi từ file hình ảnh", Toast.LENGTH_SHORT).show();
            }

        }

    }
    private void confirmAddDress(RequestBody uri,File temp, String name, String type_id,String color,String size,long price, String des){
        mViewModel.addDress(uri,temp,name,type_id,color,size,price,des);
    }
    private void mapping(View view){
        imageView = view.findViewById(R.id.imageView);

        layout_input_name = view.findViewById(R.id.layout_input_name);
        layout_input_type = view.findViewById(R.id.layout_input_type);
        layout_input_color = view.findViewById(R.id.layout_input_color);
        layout_input_size = view.findViewById(R.id.layout_input_size);
        layout_input_price = view.findViewById(R.id.layout_input_price);
        layout_input_des = view.findViewById(R.id.layout_input_des);
        btn_add_item = view.findViewById(R.id.btn_add_item);
        dropdown_type_dress = view.findViewById(R.id.dropdown_type_dress);
        dropdown_size = view.findViewById(R.id.dressSize);
    }
    private boolean validateInput(String name,String color,
                                  String price){
        boolean isValid = true;
        if(mUri ==null){
            Toast.makeText(requireContext(), "Vui lòng chọn ảnh!", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
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
        //Kiểm tra loại áo đã chọn chưa
        if(selectDressTypeFromDropdown==null){
            Toast.makeText(requireContext(), "Vui lòng chọn loại áo cưới!", Toast.LENGTH_SHORT).show();
            isValid = false;
        }else{
            layout_input_type.setError(null);
        }
        //Kiểm tra size đã chọn chưa
        if(selectSizeFromDropdown.isEmpty()){
            Toast.makeText(requireContext(), "Vui lòng chọn size áo!", Toast.LENGTH_SHORT).show();
            isValid = false;
        }else{
            layout_input_size.setError(null);
        }
        return isValid;
    }



    public void choseImgFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
//        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Đã chọn ảnh"),SELECT_PICTURE);


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

    //
    private File createTempFile() throws IOException {
        // Tạo tên tệp mới dựa trên thời gian hiện tại
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp;

        // Lấy đường dẫn tới thư mục tạm thời của ứng dụng
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Tạo tệp mới
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageView.setImageURI(selectedImageUri);
                    //
                    mUri = selectedImageUri;
                }
            }
        }
    }
}