package com.example.coffee2_app.Entrant_ui.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.coffee2_app.Entrant;
import com.example.coffee2_app.EntrantHomeActivity;
import com.example.coffee2_app.R;
import com.example.coffee2_app.databinding.FragmentEntrantProfileBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProfileFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;

    private Entrant entrant;
    private FragmentEntrantProfileBinding binding;
    private boolean isEditing = false;
    private FirebaseFirestore db;

    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;
    ImageView profilePic;

    // This method sets imageUri to imageView
    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static StorageReference getCurrentProfilePicStorageRef(){
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(currentUserId());
    }

    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            selectedImageUri = data.getData();
                            setProfilePic(getContext(), selectedImageUri, profilePic);
                        }
                    }
                });
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEntrantProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        EntrantHomeActivity activity = (EntrantHomeActivity) getActivity();
        if (activity != null) {
            entrant = activity.getEntrant();  // Get the Entrant instance
        }

        if (entrant != null) {
            displayEntrantDetails();  // Populate fields with Entrant data
        } else {
            Toast.makeText(getActivity(), "Error: Entrant data is missing.", Toast.LENGTH_SHORT).show();
        }

        // Save Button functionality
        binding.editProfileButton.setVisibility(View.GONE);  // Hide by default
        binding.editProfileButton.setOnClickListener(view -> {
            if (entrant != null) {
                saveProfileData();
                toggleEditMode();  // Exit edit mode
            }
        });

        binding.editProfileIcon.setOnClickListener(view -> toggleEditMode());
        binding.backButton.setOnClickListener(v -> getActivity().onBackPressed());

        // Profile picture click listener
        binding.profilePicture.setOnClickListener(view -> {
            if (isEditing) {
                ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512)
                        .createIntent(intent -> {
                            imagePickLauncher.launch(intent);
                            return null;
                        });
            }
        });


        // Notification settings button listener
        binding.notificationSettingsButton.setOnClickListener(view -> showNotificationSettingsDialog());

        return root;
    }

    private void displayEntrantDetails() {
        binding.entrantName.setText(entrant.getName());
        binding.entrantPhone.setText(entrant.getPhone());
        binding.entrantEmail.setText(entrant.getEmail());
    }

    private void toggleEditMode() {
        isEditing = !isEditing;
        binding.editProfileButton.setVisibility(isEditing ? View.VISIBLE : View.GONE);
        binding.editProfileIcon.setVisibility(isEditing ? View.GONE : View.VISIBLE);
        binding.notificationSettingsButton.setVisibility(isEditing ? View.GONE : View.VISIBLE);
        setEditMode(isEditing);
    }

    private void setEditMode(boolean enabled) {
        binding.entrantName.setEnabled(enabled);
        binding.entrantEmail.setEnabled(enabled);
        binding.entrantPhone.setEnabled(enabled);

        int bgColor = enabled ? getResources().getColor(android.R.color.background_light) : getResources().getColor(android.R.color.transparent);
        binding.entrantName.setBackgroundColor(bgColor);
        binding.entrantEmail.setBackgroundColor(bgColor);
        binding.entrantPhone.setBackgroundColor(bgColor);
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void showNotificationSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_notification_settings, null);
        builder.setView(dialogView);

        CheckBox adminCheckBox = dialogView.findViewById(R.id.checkbox_admin);
        CheckBox organizerCheckBox = dialogView.findViewById(R.id.checkbox_organizer);

        // Set checkbox states based on entrant's notification preferences
        if (entrant != null) {
            adminCheckBox.setChecked(entrant.getAdminNotification());
            organizerCheckBox.setChecked(entrant.getOrganizerNotification());
        }

        builder.setPositiveButton("Save", (dialog, which) -> {
            boolean receiveFromAdmin = adminCheckBox.isChecked();
            entrant.setAdminNotification(receiveFromAdmin);
            boolean receiveFromOrganizer = organizerCheckBox.isChecked();
            entrant.setOrganizerNotification(receiveFromOrganizer);
            Toast.makeText(getActivity(), "Notification preferences saved.", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dlg -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(android.R.color.black));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(android.R.color.black));
        });
        dialog.show();
    }

    void updateToFirestore(){
        if (entrant != null) {
            currentUserDetails().set(entrant)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getContext(), "Entrant data is missing", Toast.LENGTH_SHORT).show();
        }
    }

    // Inside ProfileFragment.java

    // Update the existing Entrant instance in displayEntrantDetails()

// Implement getUserData() and setInProgress()

    void getUserData() {
//        setInProgress(true);

        // Load profile picture from Firebase Storage
        getCurrentProfilePicStorageRef().getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        setProfilePic(getContext(), uri, binding.profilePicture); // updated to use binding.profilePicture
                    } else {
                        Toast.makeText(getContext(), "Failed to load profile picture.", Toast.LENGTH_SHORT).show();
                    }
                });

        // Load user data from Firestore
        currentUserDetails().get().addOnCompleteListener(task -> {
//            setInProgress(false);
            if (task.isSuccessful()) {
                entrant = task.getResult().toObject(Entrant.class); // Retrieve Entrant data
                if (entrant != null) {
                    binding.entrantName.setText(entrant.getName()); // Use Entrant's data to populate fields
                    binding.entrantPhone.setText(entrant.getPhone());
                    binding.entrantEmail.setText(entrant.getEmail());
                }
            } else {
                Toast.makeText(getContext(), "Failed to load user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    void setInProgress(boolean inProgress) {
//        if (inProgress) {
//            binding.progressBar.setVisibility(View.VISIBLE); // show progress bar
//            binding.updateProfileBtn.setVisibility(View.GONE); // hide update button while loading
//        } else {
//            binding.progressBar.setVisibility(View.GONE); // hide progress bar
//            binding.updateProfileBtn.setVisibility(View.VISIBLE); // show update button
//        }
//    }



    private void saveProfileData() {
        String name = binding.entrantName.getText().toString().trim();
        String email = binding.entrantEmail.getText().toString().trim();
        String phone = binding.entrantPhone.getText().toString().trim();

        // Update Entrant instance and Firestore
        entrant.setName(name);
        entrant.setEmail(email);
        entrant.setPhone(phone);

//        if (entrant.getId() != null) {
//            db.collection("entrants").document(entrant.getId())
//                    .set(entrant) // assuming entrant is a serializable object
//                    .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Profile updated.", Toast.LENGTH_SHORT).show())
//                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Error saving profile.", Toast.LENGTH_SHORT).show());
//        }

        if(selectedImageUri != null) {
            getCurrentProfilePicStorageRef().putFile(selectedImageUri)
                    .addOnCompleteListener(task -> {
                        updateToFirestore();
                    });
        }else{
            updateToFirestore();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
