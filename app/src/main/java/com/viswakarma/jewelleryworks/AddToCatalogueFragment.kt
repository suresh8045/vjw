package com.viswakarma.jewelleryworks

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Metal
import com.viswakarma.jewelleryworks.model.util.isValid
import com.viswakarma.jewelleryworks.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.add_to_catalogue_fragment.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddToCatalogueFragment : BaseFragment() {

    private val viewModel: AddToCatalogueViewModel by viewModel()

    private lateinit var catalogueImage: ImageView
    private lateinit var imageErrorText: TextView
    private lateinit var metalRadioGroup: RadioGroup
    private lateinit var itemType: AutoCompleteTextView
    private lateinit var itemTypeLayout: TextInputLayout
    private lateinit var weightInput: TextInputEditText
    private lateinit var weightInputLayout: TextInputLayout
    private lateinit var nameInput: TextInputEditText
    private lateinit var nameInputLayout: TextInputLayout

    private val startForAddImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val imageUri = data?.data!!
                    Toast.makeText(requireContext(), "Image: $imageUri", Toast.LENGTH_SHORT).show()
                    catalogueImage.setImageURI(imageUri)
                    viewModel.setChoosenImageBase64(requireContext(), imageUri)
                    doAddImageValidation()
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.add_to_catalogue_fragment, container, false)
    }

    override fun setupView(view: View) {
        catalogueImage = view.imageView
        imageErrorText = view.imageErrorText
        itemType = view.itemType
        itemTypeLayout = view.itemTypeLayout
        metalRadioGroup = view.radioGroupMetal
        weightInput = view.weight
        weightInputLayout = view.weightLayout
        nameInput = view.nameInput
        nameInputLayout = view.nameInputLayout
    }

    override fun setupListeners(view: View) {
        view.addPhotoFab.setOnClickListener {
            ImagePicker.with(this)
                .crop(16f, 9f)    //Crop image with 16:9 aspect ratio
                .compress(1024)
                .createIntent { intent ->
                    startForAddImageResult.launch(intent)
                }
        }

        itemType.doAfterTextChanged {
            viewModel.setSelectedItemType("")
            doItemTypeValidation(this@AddToCatalogueFragment.itemTypeLayout)
        }
        metalRadioGroup.setOnCheckedChangeListener { group, _ ->
            doMetalSelectionValidation(group)
        }
        weightInput.doAfterTextChanged {
            doWeightValidation(it.toString(), weightInputLayout)
        }
        nameInput.doAfterTextChanged {
            doItemNameValidation(it.toString(), nameInputLayout)
        }
    }

    override fun setupObservers() {
        viewModel.itemTypes.observe(viewLifecycleOwner){ itemTypes ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, itemTypes)
            itemType.run {
                setAdapter(adapter)
                setOnItemClickListener { _, _, position, _ ->
                    viewModel.setSelectedItemType(viewModel.getItemFromPos(position))
                    doItemTypeValidation(this@AddToCatalogueFragment.itemTypeLayout)
                }
            }
        }
        viewModel.isCatalogueItemAdded.observe(viewLifecycleOwner) { isDone ->
            if(isDone) {
                findNavController().navigateUp()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val actionSearch = menu.findItem(R.id.action_search)
        val actionAdd = menu.findItem(R.id.action_add)
        actionSearch.isVisible = false
        actionAdd.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.action_done -> {
            doAddImageValidation()
            doItemTypeValidation(itemTypeLayout)
            doMetalSelectionValidation(metalRadioGroup)
            doWeightValidation(weightInput.text.toString(), weightInputLayout)
            doItemNameValidation(nameInput.text.toString(), nameInputLayout)
            if (viewModel.getSelectedItemType().isNotBlank()
                && metalRadioGroup.findViewById<RadioButton>(R.id.radio_button_silver).error.isNullOrBlank()
                && weightInputLayout.error.isNullOrBlank()
                && viewModel.getChoosenImageBase64().isNotBlank()
                && nameInputLayout.error.isNullOrBlank()
            ) {
                viewModel.addCatalogueItem(
                    name = nameInput.text.toString(),
                    imageBase64 = viewModel.getChoosenImageBase64(),
                    category = "Others",
                    type = viewModel.getSelectedItemType(),
                    weight = weightInput.text.toString().toDouble(),
                    metal = getMetalChecked()!!
                )
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }


    private fun getMetalChecked(): Metal? {
        return when (metalRadioGroup.checkedRadioButtonId) {
            R.id.radio_button_gold -> {
                Metal.GOLD
            }
            R.id.radio_button_silver -> {
                Metal.SILVER
            }
            else -> null
        }
    }

    private fun doAddImageValidation(){
        if(viewModel.getChoosenImageBase64().isBlank()){
            imageErrorText.setText(R.string.add_image_error)
        }else{
            imageErrorText.text = ""
        }
    }

    private fun doItemTypeValidation(layout: TextInputLayout) {
        if (viewModel.getSelectedItemType().isBlank()) {
            layout.error = "Type Not Selected/Found"
        } else {
            layout.error = null
        }
    }

    private fun doMetalSelectionValidation(view: RadioGroup) {
        when (view.checkedRadioButtonId) {
            View.NO_ID -> {
                view.findViewById<RadioButton>(R.id.radio_button_silver).error = "Select Metal Type"
            }
            R.id.radio_button_gold -> {
                view.findViewById<RadioButton>(R.id.radio_button_silver).error = null
            }
            R.id.radio_button_silver -> {
                view.findViewById<RadioButton>(R.id.radio_button_silver).error = null
            }
        }
    }

    private fun doWeightValidation(text: String, layout: TextInputLayout) {
        if (text.isValid().not() && text.toDoubleOrNull() == null) {
            layout.error = "Invalid Weight"
        } else {
            layout.error = null
        }
    }

    private fun doItemNameValidation(text: String, layout: TextInputLayout) {
        if (text.isValid().not()) {
            layout.error = "Name must not be Empty"
        } else {
            layout.error = null
        }
    }
}