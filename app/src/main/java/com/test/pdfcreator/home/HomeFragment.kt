package com.test.pdfcreator.home

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfChunk
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.test.pdfcreator.databinding.CustomLayoutBinding
import com.test.pdfcreator.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    private val calendar = Calendar.getInstance()
    private var _binding: FragmentHomeBinding? = null

    private val mViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // createFile()

    }

    // Request code for creating a PDF document.

    val CREATE_FILE = 1
    private val launcher: ActivityResultLauncher<Intent>? =
        null // Initialise this object in Activity.onCreate()
    private val baseDocumentTreeUri: Uri? = null
    private fun createFile() {
//        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
//            addCategory(Intent.CATEGORY_OPENABLE)
//            type = "application/pdf"
//            putExtra(Intent.EXTRA_TITLE, "invoice.pdf")
//
//            // Optionally, specify a URI for the directory that should be opened in
//            // the system file picker before your app creates the document.
//            // putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
//        }
//        startActivityForResult(intent, CREATE_FILE)

        fileCreate()
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
//        launcher?.launch(intent)
    }


    override fun onActivityResult(
        requestCode: Int, resultCode: Int, resultData: Intent?
    ) {
        if (resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            resultData?.data?.also { uri ->
                // Perform operations on the document using its URI.
                val contentResolver = requireActivity().applicationContext.contentResolver
                val takeFlags: Int =
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                contentResolver.takePersistableUriPermission(uri, takeFlags)
                // writeFileContent(uri)
            }
        }
    }

//    private fun writeFileContent(data: Uri?) {
//        try {
//            val file = data?.let { requireActivity().contentResolver.openFileDescriptor(it, "w") }
//            file?.let {
//                val fileOutputStream = FileOutputStream(it.fileDescriptor)
//                val document = Document(PageSize.A4, 36f, 36f, 16f, 36f)
//
//                try {
//                    // Create a PdfWriter instance
//                    val writer = PdfWriter.getInstance(document, fileOutputStream)
//
//                    // Open the document for writing
//                    document.open()
//
//                    // Add title to the document
//                    val title = "Tax Invoice"
//                    val titleParagraph = Paragraph(title)
//                    titleParagraph.alignment = Paragraph.ALIGN_CENTER
//                    document.add(titleParagraph)
//
//
//                    val table = PdfPTable(3)
//                    table.widthPercentage = 100f
//                    table.defaultCell.borderColor = BaseColor.BLACK
//
//                    table.setWidths(floatArrayOf(2f, 1f, 1f))
//
//                    // Create cells
//                    val firstCell = PdfPCell(
//                        Phrase(
//                            _binding?.address?.text.toString()
//                        )
//                    )
//                    firstCell.rowspan = 6 // Span three columns
//
//                    var invoice =
//                        "Invoice No. e-Way Bill No.\n" + _binding?.invoice?.text.toString() + "\n"
//                    var invoiceCell = phraseText(invoice)
//
//                    var date = "Dated\n" + _binding?.date?.text.toString() + "\n"
//                    var dateCell = phraseText(date)
//
//                    var delivery = "Delivery Note\n\n"
//                    var deliveryCell = phraseText(delivery)
//
//                    var payment = "Mode/Terms of Payment\n\n"
//                    var paymentCell = phraseText(payment)
//
//                    var reference = "Reference No. & Date\n\n"
//                    var referenceCell = phraseText(reference)
//
//                    var otherreference = "Other References\n\n"
//                    var otherreferenceCell = phraseText(otherreference)
//
//
//                    val secondAddressCell = PdfPCell(
//                        Phrase(
//                            _binding?.address1?.text.toString()
//                        )
//                    )
//                    secondAddressCell.rowspan = 6 // Span three columns
//
//                    var buyersorderno = "Buyer's Order No\n\n"
//                    var buyersordernoCell = phraseText(buyersorderno)
//
//                    var dated = "Dated\n\n"
//                    var datedCell = phraseText(dated)
//
//                    var dispatchdoc = "Dispatch Doc No.\n\n"
//                    var dispatchdocCell = phraseText(dispatchdoc)
//
//                    var deliverynotedate = "Delivery Note Date\n\n"
//                    var deliverynotedateCell = phraseText(deliverynotedate)
//
//                    var dispatchdthrough = "Dispatch through\n\n"
//                    var dispatchdthroughCell = phraseText(dispatchdthrough)
//
//                    var destination = "Destination\n\n"
//                    var destinationCell = phraseText(destination)
//
//
//                    val thirdAddressCell = PdfPCell(
//                        Phrase(
//                            _binding?.address2?.text?.toString()
//                        )
//                    )
//                    thirdAddressCell.rowspan = 6 // Span three columns
//
//                    var termsofdelivery = "Terms of Delivery\n\n"
//                    var termsofdeliveryCell = phraseText(termsofdelivery)
//                    termsofdeliveryCell.rowspan = 6
//                    termsofdeliveryCell.colspan = 2
//
//                    // Add cells to the table
//                    table.addCell(firstCell)
//                    table.addCell(invoiceCell)
//                    table.addCell(dateCell)
//                    table.addCell(deliveryCell)
//                    table.addCell(paymentCell)
//                    table.addCell(referenceCell)
//                    table.addCell(otherreferenceCell)
//
//                    table.addCell(secondAddressCell)
//                    table.addCell(buyersordernoCell)
//                    table.addCell(datedCell)
//                    table.addCell(dispatchdocCell)
//                    table.addCell(deliverynotedateCell)
//                    table.addCell(dispatchdthroughCell)
//                    table.addCell(destinationCell)
//
//                    table.addCell(thirdAddressCell)
//                    table.addCell(termsofdeliveryCell)
//
//                    document.add(table)
//
//                    val secondtable = PdfPTable(7)
//                    secondtable.widthPercentage = 100f
//                    secondtable.defaultCell.borderColor = BaseColor.BLACK
//
//                    secondtable.setWidths(floatArrayOf(.1f, 1f, .4f, .4f, .4f, .2f, .5f))
//
//                    var slno = "SI No."
//                    var slnoCell = phraseText(slno)
//
//                    var description = "Description of Goods"
//                    var descriptionCell = phraseText(description)
//
//                    var hsn = "HSN/SAC"
//                    var hsnCell = phraseText(hsn)
//
//                    var quantity = "Quantity"
//                    var quantityCell = phraseText(quantity)
//
//                    var rate = "Rate"
//                    var rateCell = phraseText(rate)
//
//                    var per = "Per"
//                    var perCell = phraseText(per)
//
//                    var amount = "Amount"
//                    var amountCell = phraseText(amount)
//
//                    secondtable.addCell(slnoCell)
//                    secondtable.addCell(descriptionCell)
//                    secondtable.addCell(hsnCell)
//                    secondtable.addCell(quantityCell)
//                    secondtable.addCell(rateCell)
//                    secondtable.addCell(perCell)
//                    secondtable.addCell(amountCell)
//
//                    val cell1 = phraseText("1")
//                    cell1.border = PdfPCell.LEFT or PdfPCell.RIGHT // Set no border
//
//                    val cell2 = phraseText(_binding?.name?.text.toString())
//                    cell2.border = PdfPCell.RIGHT // Set no border
//
//                    val cell3 = phraseText(_binding?.hsnsac?.text.toString())
//                    cell3.border = PdfPCell.RIGHT // Set no border
//
//                    val cell4 = phraseText(_binding?.quantity?.text.toString() + " NOS")
//                    cell4.border = PdfPCell.RIGHT // Set no border
//
//                    val cell5 = phraseText(_binding?.price?.text.toString())
//                    cell5.border = PdfPCell.RIGHT // Set no border
//
//                    val cell6 = phraseText("NOS")
//                    cell6.border = PdfPCell.RIGHT // Set no border
//
//                    var totalQuantity = _binding?.quantity?.text.toString().toDouble()
//                    var priceQuantity = _binding?.price?.text.toString().toDouble()
//                    var totalAmount = totalQuantity * priceQuantity
//
//                    val cell7 = phraseText(totalAmount.toString())
//                    cell7.border = PdfPCell.RIGHT // Set no border
//
//                    secondtable.addCell(cell1)
//                    secondtable.addCell(cell2)
//                    secondtable.addCell(cell3)
//                    secondtable.addCell(cell4)
//                    secondtable.addCell(cell5)
//                    secondtable.addCell(cell6)
//                    secondtable.addCell(cell7)
//
//                    val cell11 = phraseText("")
//                    cell11.paddingTop = 30f
//                    cell11.border = PdfPCell.LEFT or PdfPCell.RIGHT
//
//                    val cell12 = phraseText("OUTPUT CGST 2.5%")
//                    cell12.paddingTop = 30f
//                    cell12.border = PdfPCell.RIGHT // Set no border
//
//                    val cell13 = phraseText("")
//                    cell13.paddingTop = 30f
//                    cell13.border = PdfPCell.RIGHT // Set no border
//
//                    val cell14 = phraseText("")
//                    cell14.paddingTop = 30f
//                    cell14.border = PdfPCell.RIGHT // Set no border
//
//                    val cell15 = phraseText("2.50")
//                    cell15.paddingTop = 30f
//                    cell15.border = PdfPCell.RIGHT // Set no border
//
//                    val cell16 = phraseText("%")
//                    cell16.paddingTop = 30f
//                    cell16.border = PdfPCell.RIGHT // Set no border
//
//                    val cell17 = phraseText("72035.586")
//                    cell17.paddingTop = 30f
//                    cell17.border = PdfPCell.RIGHT // Set no border
//
//                    secondtable.addCell(cell11)
//                    secondtable.addCell(cell12)
//                    secondtable.addCell(cell13)
//                    secondtable.addCell(cell14)
//                    secondtable.addCell(cell15)
//                    secondtable.addCell(cell16)
//                    secondtable.addCell(cell17)
//
//
//                    val cell21 = phraseText("")
//                    cell21.border = PdfPCell.LEFT or PdfPCell.RIGHT
//
//                    val cell22 = phraseText("OUTPUT CGST 2.5%")
//                    cell22.border = PdfPCell.RIGHT // Set no border
//
//                    val cell23 = phraseText("")
//                    cell23.border = PdfPCell.RIGHT // Set no border
//
//                    val cell24 = phraseText("")
//                    cell24.border = PdfPCell.RIGHT // Set no border
//
//                    val cell25 = phraseText("2.50")
//                    cell25.border = PdfPCell.RIGHT // Set no border
//
//                    val cell26 = phraseText("%")
//                    cell26.border = PdfPCell.RIGHT // Set no border
//
//                    val cell27 = phraseText("72035.586")
//                    cell27.border = PdfPCell.RIGHT // Set no border
//
//                    secondtable.addCell(cell21)
//                    secondtable.addCell(cell22)
//                    secondtable.addCell(cell23)
//                    secondtable.addCell(cell24)
//                    secondtable.addCell(cell25)
//                    secondtable.addCell(cell26)
//                    secondtable.addCell(cell27)
//
//
//                    val cell31 = phraseText("")
//                    cell31.border = PdfPCell.LEFT or PdfPCell.RIGHT
//
//                    val cell32 = phraseText("ROUND OFF")
//                    cell32.border = PdfPCell.RIGHT // Set no border
//
//                    val cell33 = phraseText("")
//                    cell33.border = PdfPCell.RIGHT // Set no border
//
//                    val cell34 = phraseText("")
//                    cell34.border = PdfPCell.RIGHT // Set no border
//
//                    val cell35 = phraseText("")
//                    cell35.border = PdfPCell.RIGHT // Set no border
//
//                    val cell36 = phraseText("")
//                    cell36.border = PdfPCell.RIGHT // Set no border
//
//                    val cell37 = phraseText("72035.586")
//                    cell37.border = PdfPCell.RIGHT // Set no border
//
//                    secondtable.addCell(cell31)
//                    secondtable.addCell(cell32)
//                    secondtable.addCell(cell33)
//                    secondtable.addCell(cell34)
//                    secondtable.addCell(cell35)
//                    secondtable.addCell(cell36)
//                    secondtable.addCell(cell37)
//
//
//                    val cell41 = phraseText("")
//                    // cell41.border = PdfPCell.
//
//                    val cell42 = phraseText("TOTAL")
//                    //cell42.border = PdfPCell.RIGHT // Set no border
//
//                    val cell43 = phraseText("")
//                    //cell43.border = PdfPCell.RIGHT // Set no border
//
//                    val cell44 = phraseText("1449 NOS")
//                    // cell44.border = PdfPCell.RIGHT // Set no border
//
//                    val cell45 = phraseText("")
//                    //cell45.border = PdfPCell.RIGHT // Set no border
//
//                    val cell46 = phraseText("")
//                    // cell46.border = PdfPCell.RIGHT // Set no border
//
//                    val cell47 = phraseText("72035.586")
//                    //  cell47.border = PdfPCell.RIGHT // Set no border
//
//                    secondtable.addCell(cell41)
//                    secondtable.addCell(cell42)
//                    secondtable.addCell(cell43)
//                    secondtable.addCell(cell44)
//                    secondtable.addCell(cell45)
//                    secondtable.addCell(cell46)
//                    secondtable.addCell(cell47)
//
//
//                    document.add(secondtable)
//
//                    val thirdtable = PdfPTable(2)
//                    thirdtable.widthPercentage = 100f
//                    thirdtable.defaultCell.borderColor = BaseColor.BLACK
//
//                    val cellamountchargeable = phraseText("Amount Chargeable(in words)")
//                    cellamountchargeable.border = PdfPCell.LEFT
//
//                    val cellamountchargeableright = phraseTextRight("Amount")
//                    cellamountchargeableright.border = PdfPCell.RIGHT
//
//                    thirdtable.addCell(cellamountchargeable)
//                    thirdtable.addCell(cellamountchargeableright)
//
//                    document.add(thirdtable)
//
//                    val fourthtable = PdfPTable(1)
//                    fourthtable.widthPercentage = 100f
//                    fourthtable.defaultCell.borderColor = BaseColor.BLACK
//
//                    val cellamount = PdfPCell(Phrase("INR Seventy Five Thousand Six Hundred Only"))
//                    cellamount.border = PdfPCell.RIGHT or PdfPCell.LEFT or PdfPCell.BOTTOM
//                    fourthtable.addCell(cellamount)
//
//                    document.add(fourthtable)
//
//                    val fifthtable = PdfPTable(7)
//                    fifthtable.widthPercentage = 100f
//                    fifthtable.defaultCell.borderColor = BaseColor.BLACK
//                    fifthtable.setWidths(floatArrayOf(.5f, .5f, .4f, .5f, .4f, .4f, .5f))
//
//                    val cell51 = phraseTextCenter("HSN/SAC")
//                    val cell52 = phraseText("Taxable Value")
//                    val cell53 = phraseText("Central Tax Rate")
//                    val cell54 = phraseText("Central Tax Amount")
//                    val cell55 = phraseText("State Tax Rate")
//                    val cell56 = phraseText("State Tax Amount")
//                    val cell57 = phraseText("Total Tax Amount")
//
//
//
//                    fifthtable.addCell(cell51)
//                    fifthtable.addCell(cell52)
//                    fifthtable.addCell(cell53)
//                    fifthtable.addCell(cell54)
//                    fifthtable.addCell(cell55)
//                    fifthtable.addCell(cell56)
//                    fifthtable.addCell(cell57)
//
//                    val cell61 = phraseText("2516")
//                    val cell62 = phraseText("72035.586")
//                    val cell63 = phraseText("2.50%")
//                    val cell64 = phraseText("1800.890")
//                    val cell65 = phraseText("2.50%")
//                    val cell66 = phraseText("1800.890")
//                    val cell67 = phraseText("3601.780")
//
//                    fifthtable.addCell(cell61)
//                    fifthtable.addCell(cell62)
//                    fifthtable.addCell(cell63)
//                    fifthtable.addCell(cell64)
//                    fifthtable.addCell(cell65)
//                    fifthtable.addCell(cell66)
//                    fifthtable.addCell(cell67)
//
//                    val cell71 = phraseTextRightAlign("Total")
//                    val cell72 = phraseText("72035.586")
//                    val cell73 = phraseText("")
//                    val cell74 = phraseText("1800.890")
//                    val cell75 = phraseText("")
//                    val cell76 = phraseText("1800.890")
//                    val cell77 = phraseText("3601.780")
//
//                    fifthtable.addCell(cell71)
//                    fifthtable.addCell(cell72)
//                    fifthtable.addCell(cell73)
//                    fifthtable.addCell(cell74)
//                    fifthtable.addCell(cell75)
//                    fifthtable.addCell(cell76)
//                    fifthtable.addCell(cell77)
//
//                    document.add(fifthtable)
//
//                    val sixthtable = PdfPTable(1)
//                    sixthtable.widthPercentage = 100f
//                    sixthtable.defaultCell.borderColor = BaseColor.BLACK
//
//                    val cell78 = phraseText("Thousand Two Hunderd Tewenty Five")
//                    cell78.paddingBottom = 40f
//                    cell78.border = PdfPCell.LEFT or PdfPCell.RIGHT
//
//                    sixthtable.addCell(cell78)
//                    document.add(sixthtable)
//
//                    val seventhtable = PdfPTable(2)
//                    seventhtable.widthPercentage = 100f
//                    seventhtable.defaultCell.borderColor = BaseColor.BLACK
//
//                    val cell79 = phraseText(
//                        "Declaration\n" +
//                                "We declare that this Invoce is"
//                    )
//                    cell79.border = PdfPCell.LEFT or PdfPCell.RIGHT or PdfPCell.BOTTOM
//
//                    val cell80 =
//                        phraseTextRight("for STORAGE INDUSTRIES\n\n\nAuthorized Signatory")
//                    cell80.border =
//                        PdfPCell.LEFT or PdfPCell.RIGHT or PdfPCell.BOTTOM or PdfPCell.TOP
//
//                    seventhtable.addCell(cell79)
//                    seventhtable.addCell(cell80)
//                    document.add(seventhtable)
//
//                    val eighthtable = PdfPTable(1)
//                    eighthtable.widthPercentage = 100f
//                    eighthtable.defaultCell.borderColor = BaseColor.BLACK
//
//                    val cell81 =
//                        phraseTextCenter("This is a Computer Generated Invoice")
//                    cell81.border =
//                        PdfPCell.NO_BORDER
//
//                    eighthtable.addCell(cell81)
//                    document.add(eighthtable)
//
//                    document.close()
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//                fileOutputStream.close()
//                it.close()
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }

    private fun writeFileContent() {
        try {

            var sgst = 0.0
            var cgst = 0.0
            var isIgst = false
            var totalTax = _binding?.etTax?.text?.toString()?.toDouble()

            if (_binding?.rbSgstCgst?.isChecked == true) {
                isIgst = false
            } else {
                isIgst = true
            }
            if (totalTax != null) {
                sgst = (totalTax / 2)
                cgst = (totalTax / 2)
            }


            val downloadsDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val pdfFile = File(downloadsDir, "pdftestinvoice.pdf")

            val fos: OutputStream = FileOutputStream(pdfFile)

            val document = Document(PageSize.A4, 36f, 36f, 16f, 36f)

            try {
                // Create a PdfWriter instance
                val writer = PdfWriter.getInstance(document, fos)

                // Open the document for writing
                document.open()

                // Add title to the document
                val title = "Tax Invoice"
                val titleParagraph = Paragraph(title)
                titleParagraph.alignment = Paragraph.ALIGN_CENTER
                document.add(titleParagraph)


                val table = PdfPTable(3)
                table.widthPercentage = 100f
                table.defaultCell.borderColor = BaseColor.BLACK

                table.setWidths(floatArrayOf(2f, 1f, 1f))

                // Create cells
                val firstCell = PdfPCell(
                    Phrase(
                        _binding?.address?.text.toString()
                    )
                )
                firstCell.rowspan = 6 // Span three columns

                var invoice =
                    "Invoice No. e-Way Bill No.\n" + _binding?.invoice?.text.toString() + "\n"
                var invoiceCell = phraseText(invoice)

                var date = "Dated\n" + _binding?.date?.text.toString() + "\n"
                var dateCell = phraseText(date)

                var delivery = "Delivery Note\n\n"
                var deliveryCell = phraseText(delivery)

                var payment = "Mode/Terms of Payment\n\n"
                var paymentCell = phraseText(payment)

                var reference = "Reference No. & Date\n\n"
                var referenceCell = phraseText(reference)

                var otherreference = "Other References\n\n"
                var otherreferenceCell = phraseText(otherreference)


                val secondAddressCell = PdfPCell(
                    Phrase(
                        _binding?.address1?.text.toString()
                    )
                )
                secondAddressCell.rowspan = 6 // Span three columns

                var buyersorderno = "Buyer's Order No\n\n"
                var buyersordernoCell = phraseText(buyersorderno)

                var dated = "Dated\n\n"
                var datedCell = phraseText(dated)

                var dispatchdoc = "Dispatch Doc No.\n\n"
                var dispatchdocCell = phraseText(dispatchdoc)

                var deliverynotedate = "Delivery Note Date\n\n"
                var deliverynotedateCell = phraseText(deliverynotedate)

                var dispatchdthrough = "Dispatch through\n\n"
                var dispatchdthroughCell = phraseText(dispatchdthrough)

                var destination = "Destination\n\n"
                var destinationCell = phraseText(destination)


                val thirdAddressCell = PdfPCell(
                    Phrase(
                        _binding?.address2?.text?.toString()
                    )
                )
                thirdAddressCell.rowspan = 6 // Span three columns

                var termsofdelivery = "Terms of Delivery\n\n"
                var termsofdeliveryCell = phraseText(termsofdelivery)
                termsofdeliveryCell.rowspan = 6
                termsofdeliveryCell.colspan = 2

                // Add cells to the table
                table.addCell(firstCell)
                table.addCell(invoiceCell)
                table.addCell(dateCell)
                table.addCell(deliveryCell)
                table.addCell(paymentCell)
                table.addCell(referenceCell)
                table.addCell(otherreferenceCell)

                table.addCell(secondAddressCell)
                table.addCell(buyersordernoCell)
                table.addCell(datedCell)
                table.addCell(dispatchdocCell)
                table.addCell(deliverynotedateCell)
                table.addCell(dispatchdthroughCell)
                table.addCell(destinationCell)

                table.addCell(thirdAddressCell)
                table.addCell(termsofdeliveryCell)

                document.add(table)

                val secondtable = PdfPTable(7)
                secondtable.widthPercentage = 100f
                secondtable.defaultCell.borderColor = BaseColor.BLACK

                secondtable.setWidths(floatArrayOf(.1f, 1f, .4f, .4f, .4f, .2f, .5f))

                var slno = "SI No."
                var slnoCell = phraseText(slno)

                var description = "Description of Goods"
                var descriptionCell = phraseText(description)

                var hsn = "HSN/SAC"
                var hsnCell = phraseText(hsn)

                var quantity = "Quantity"
                var quantityCell = phraseText(quantity)

                var rate = "Rate"
                var rateCell = phraseText(rate)

                var per = "Per"
                var perCell = phraseText(per)

                var amount = "Amount"
                var amountCell = phraseText(amount)

                secondtable.addCell(slnoCell)
                secondtable.addCell(descriptionCell)
                secondtable.addCell(hsnCell)
                secondtable.addCell(quantityCell)
                secondtable.addCell(rateCell)
                secondtable.addCell(perCell)
                secondtable.addCell(amountCell)

                val cell1 = phraseText("1")
                cell1.border = PdfPCell.LEFT or PdfPCell.RIGHT // Set no border

                val cell2 = phraseText(_binding?.name?.text.toString())
                cell2.border = PdfPCell.RIGHT // Set no border

                val cell3 = phraseText(_binding?.hsnsac?.text.toString())
                cell3.border = PdfPCell.RIGHT // Set no border

                val cell4 = phraseText(_binding?.quantity?.text.toString() + " NOS")
                cell4.border = PdfPCell.RIGHT // Set no border

                val cell5 = phraseTextRightAlign(_binding?.price?.text.toString())
                cell5.border = PdfPCell.RIGHT // Set no border

                val cell6 = phraseText("NOS")
                cell6.border = PdfPCell.RIGHT // Set no border

                var totalQuantity = _binding?.quantity?.text.toString()
                var priceQuantity = _binding?.price?.text.toString()

                var totalAmount = totalQuantity.toDouble() * priceQuantity.toDouble()
                totalAmount = roundToThreeDecimalPlaces(totalAmount)


                var cell7: PdfPCell
                if (_binding?.cbTaxInclusive?.isChecked == false) {
                    cell7 = phraseTextRightRowSpan(totalAmount.toString())
                    cell7.border = PdfPCell.RIGHT // Set no border
                } else {
                    var totalGstRate = totalTax
                    cell7 = phraseTextRightRowSpan(
                        calculateNetAmount(
                            totalAmount,
                            totalGstRate
                        ).toString()
                    )
                    cell7.border = PdfPCell.RIGHT // Set no border
                }

                secondtable.addCell(cell1)
                secondtable.addCell(cell2)
                secondtable.addCell(cell3)
                secondtable.addCell(cell4)
                secondtable.addCell(cell5)
                secondtable.addCell(cell6)
                secondtable.addCell(cell7)

                val cell11 = phraseText("")
                cell11.paddingTop = 30f
                cell11.border = PdfPCell.LEFT or PdfPCell.RIGHT

                var cell12 = PdfPCell()
                if (isIgst) {
                    cell12 =
                        phraseTextRightRowSpan("OUTPUT IGST " + totalTax.toString() + " %")
                } else {
                    cell12 =
                        phraseTextRightRowSpan("OUTPUT CGST " + cgst.toString() + " %")
                }

                cell12.paddingTop = 30f
                cell12.border = PdfPCell.RIGHT // Set no border

                val cell13 = phraseText("")
                cell13.paddingTop = 30f
                cell13.border = PdfPCell.RIGHT // Set no border

                val cell14 = phraseText("")
                cell14.paddingTop = 30f
                cell14.border = PdfPCell.RIGHT // Set no border

                var cell15 = PdfPCell()
                cell15.paddingTop = 30f
                if (isIgst) {
                    cell15 = phraseTextRightAlign(totalTax.toString())
                } else {
                    cell15 = phraseTextRightAlign(cgst.toString())
                }
                cell15.paddingTop = 30f
                cell15.border = PdfPCell.RIGHT // Set no border

                val cell16 = phraseText("%")
                cell16.paddingTop = 30f
                cell16.border = PdfPCell.RIGHT // Set no border

                var cgstAmount = 0.0
                var sgstAmount = 0.0

                if (_binding?.cbTaxInclusive?.isChecked == false) {
                    cgstAmount = cgst * totalAmount / 100
                    cgstAmount = roundToThreeDecimalPlaces(cgstAmount)
                    sgstAmount = sgst * totalAmount / 100
                    sgstAmount = roundToThreeDecimalPlaces(sgstAmount)
                } else {
                    cgstAmount = calculateTaxAmount(totalAmount, cgst)
                    cgstAmount = roundToThreeDecimalPlaces(cgstAmount)
                    sgstAmount = calculateTaxAmount(totalAmount, sgst)
                    sgstAmount = roundToThreeDecimalPlaces(sgstAmount)
                }

                var cell17 = PdfPCell()

                if (isIgst) {
                    cell17 = phraseTextRightRowSpan((cgstAmount + sgstAmount).toString())
                    cell17.paddingTop = 30f
                    cell17.border = PdfPCell.RIGHT // Set no border
                } else {
                    cell17 = phraseTextRightRowSpan(cgstAmount.toString())
                    cell17.paddingTop = 30f
                    cell17.border = PdfPCell.RIGHT // Set no border
                }


                secondtable.addCell(cell11)
                secondtable.addCell(cell12)
                secondtable.addCell(cell13)
                secondtable.addCell(cell14)
                secondtable.addCell(cell15)
                secondtable.addCell(cell16)
                secondtable.addCell(cell17)


                if (!isIgst) {
                    val cell21 = phraseText("")
                    cell21.border = PdfPCell.LEFT or PdfPCell.RIGHT

                    val cell22 =
                        phraseTextRightRowSpan("OUTPUT SGST " + sgst.toString() + " %")
                    cell22.border = PdfPCell.RIGHT // Set no border

                    val cell23 = phraseText("")
                    cell23.border = PdfPCell.RIGHT // Set no border

                    val cell24 = phraseText("")
                    cell24.border = PdfPCell.RIGHT // Set no border

                    val cell25 = phraseTextRightAlign(sgst.toString())
                    cell25.border = PdfPCell.RIGHT // Set no border

                    val cell26 = phraseText("%")
                    cell26.border = PdfPCell.RIGHT // Set no border

                    val cell27 = phraseTextRightRowSpan(sgstAmount.toString())
                    cell27.border = PdfPCell.RIGHT // Set no border

                    secondtable.addCell(cell21)
                    secondtable.addCell(cell22)
                    secondtable.addCell(cell23)
                    secondtable.addCell(cell24)
                    secondtable.addCell(cell25)
                    secondtable.addCell(cell26)
                    secondtable.addCell(cell27)
                }

                val cell31 = phraseText("")
                cell31.border = PdfPCell.LEFT or PdfPCell.RIGHT

                val cell32 = phraseTextRightRowSpan("Less:            ROUND OFF")
                cell32.border = PdfPCell.RIGHT // Set no border

                val cell33 = phraseText("")
                cell33.border = PdfPCell.RIGHT // Set no border

                val cell34 = phraseText("")
                cell34.border = PdfPCell.RIGHT // Set no border

                val cell35 = phraseText("")
                cell35.border = PdfPCell.RIGHT // Set no border

                val cell36 = phraseText("")
                cell36.border = PdfPCell.RIGHT // Set no border

                var totalAmountWithTax = 0.0
                if (_binding?.cbTaxInclusive?.isChecked == false) {
                    totalAmountWithTax = cgstAmount + sgstAmount + totalAmount
                    totalAmountWithTax = roundToThreeDecimalPlaces(totalAmountWithTax)
                } else {
                    totalAmountWithTax = totalAmount
                    totalAmountWithTax = roundToThreeDecimalPlaces(totalAmountWithTax)
                }
                // val rounded1 = Math.round(totalAmountWithTax).toInt()
                //  val roundedNumber1 = floor(totalAmountWithTax)

                val round = roundDown(totalAmountWithTax)
                var roundeddecimal = getDecimalValue(totalAmountWithTax)
                roundeddecimal = roundToThreeDecimalPlaces(roundeddecimal)

                val cell37 = phraseTextRightRowSpan("(-)" + roundeddecimal.toString())
                cell37.border = PdfPCell.RIGHT // Set no border

                secondtable.addCell(cell31)
                secondtable.addCell(cell32)
                secondtable.addCell(cell33)
                secondtable.addCell(cell34)
                secondtable.addCell(cell35)
                secondtable.addCell(cell36)
                secondtable.addCell(cell37)


                val cell41 = phraseText("")
                // cell41.border = PdfPCell.

                val cell42 = phraseTextRightRowSpan("TOTAL")
                //cell42.border = PdfPCell.RIGHT // Set no border

                val cell43 = phraseText("")
                //cell43.border = PdfPCell.RIGHT // Set no border

                val cell44 = phraseText(_binding?.quantity?.text.toString() + " NOS")
                // cell44.border = PdfPCell.RIGHT // Set no border

                val cell45 = phraseText("")
                //cell45.border = PdfPCell.RIGHT // Set no border

                val cell46 = phraseText("")
                // cell46.border = PdfPCell.RIGHT // Set no border

                val cell47 = phraseTextRightRowSpan("₹$round")
                //  cell47.border = PdfPCell.RIGHT // Set no border

                secondtable.addCell(cell41)
                secondtable.addCell(cell42)
                secondtable.addCell(cell43)
                secondtable.addCell(cell44)
                secondtable.addCell(cell45)
                secondtable.addCell(cell46)
                secondtable.addCell(cell47)


                document.add(secondtable)

                val thirdtable = PdfPTable(2)
                thirdtable.widthPercentage = 100f
                thirdtable.defaultCell.borderColor = BaseColor.BLACK

                val cellamountchargeable = phraseText("Amount Chargeable(in words)")
                cellamountchargeable.border = PdfPCell.LEFT

                val cellamountchargeableright = phraseTextRight("E. & O.E")
                cellamountchargeableright.border = PdfPCell.RIGHT

                thirdtable.addCell(cellamountchargeable)
                thirdtable.addCell(cellamountchargeableright)

                document.add(thirdtable)

                val fourthtable = PdfPTable(1)
                fourthtable.widthPercentage = 100f
                fourthtable.defaultCell.borderColor = BaseColor.BLACK


                val cellamount =
                    PdfPCell(
                        Phrase("INR " + amountToWordsIndianFormat(round))
                    )
                cellamount.border = PdfPCell.RIGHT or PdfPCell.LEFT or PdfPCell.BOTTOM
                fourthtable.addCell(cellamount)

                document.add(fourthtable)

                var fifthtable: PdfPTable
                if (isIgst) {
                    fifthtable = PdfPTable(4)
                } else {
                    fifthtable = PdfPTable(5)
                }
                fifthtable.widthPercentage = 100f
                fifthtable.defaultCell.borderColor = BaseColor.BLACK
                if (isIgst) {
                    fifthtable.setWidths(floatArrayOf(.5f, .5f, .5f, .5f))
                } else {
                    fifthtable.setWidths(floatArrayOf(.5f, .5f, .5f, .5f, .5f))
                }

                val cell51 = phraseTextCenter("HSN/SAC")
                val cell52 = phraseTextCenter("Taxable Value")
                var cell53: PdfPCell
                var cell55 = PdfPCell()
                if (!isIgst) {
                    cell53 = phraseTableInsideCell("Central Tax", "Rate", "Amount")
                    cell55 = phraseTableInsideCell("State Tax ", "Rate", "Amount")
                } else {
                    cell53 = phraseTableInsideCell("IGST", "Rate", "Amount")
                }
                val cell57 = phraseTextCenter("Total Tax Amount")

                fifthtable.addCell(cell51)
                fifthtable.addCell(cell52)
                if (!isIgst) {
                    fifthtable.addCell(cell53)
                    fifthtable.addCell(cell55)
                } else {
                    fifthtable.addCell(cell53)
                }
                fifthtable.addCell(cell57)

                val cell61 = phraseTextCenter(_binding?.hsnsac?.text.toString())

                val cell62: PdfPCell
                if (_binding?.cbTaxInclusive?.isChecked == false) {
                    cell62 = phraseTextRightColSpan(totalAmount.toString())
                } else {
                    var totalGstRate = totalTax
                    cell62 = phraseTextRightColSpan(
                        calculateNetAmount(
                            totalAmount,
                            totalGstRate
                        ).toString()
                    )
                }


                var cell63 = PdfPCell()
                var cell65 = PdfPCell()

                if (!isIgst) {
                    cell63 =
                        phraseTableInsideCellRow(
                            cgst.toString() + "%",
                            cgstAmount.toString()
                        )
                    cell65 =
                        phraseTableInsideCellRow(
                            sgst.toString() + "%",
                            sgstAmount.toString()
                        )
                } else {
                    cell63 =
                        phraseTableInsideCellRow(
                            totalTax.toString() + "%",
                            (cgstAmount + sgstAmount).toString()
                        )
                }

                var totalTax = cgstAmount + sgstAmount

                val cell67 = phraseTextRightColSpan(totalTax.toString())

                fifthtable.addCell(cell61)
                fifthtable.addCell(cell62)
                if (!isIgst) {
                    fifthtable.addCell(cell63)
                    fifthtable.addCell(cell65)
                } else {
                    fifthtable.addCell(cell63)
                }
                fifthtable.addCell(cell67)

                val cell71 = phraseTextRightColSpan("Total")

                val cell72: PdfPCell
                if (_binding?.cbTaxInclusive?.isChecked == false) {
                    cell72 = phraseTextRightColSpan(totalAmount.toString())
                } else {
                    var totalGstRate = totalTax
                    cell72 = phraseTextRightColSpan(
                        calculateNetAmount(
                            totalAmount,
                            totalGstRate
                        ).toString()
                    )
                }
                var cell73 = PdfPCell()
                var cell75 = PdfPCell()
                if (!isIgst) {
                    cell73 = phraseTableInsideCellRow("", cgstAmount.toString())
                    cell75 = phraseTableInsideCellRow("", sgstAmount.toString())
                } else {
                    cell73 = phraseTableInsideCellRow("", (cgstAmount + sgstAmount).toString())
                }
                val cell77 = phraseTextRightColSpan(totalTax.toString())

                fifthtable.addCell(cell71)
                fifthtable.addCell(cell72)
                if (!isIgst) {
                    fifthtable.addCell(cell73)
                    fifthtable.addCell(cell75)
                } else {
                    fifthtable.addCell(cell73)
                }
                fifthtable.addCell(cell77)

                document.add(fifthtable)

                val sixthtable = PdfPTable(1)
                sixthtable.widthPercentage = 100f
                sixthtable.defaultCell.borderColor = BaseColor.BLACK

                val return_val_in_english = amountToWordsIndianFormat(totalTax)

                val cell78 = phraseText("Tax Amount(in words)" + return_val_in_english)
                cell78.paddingBottom = 40f
                cell78.border = PdfPCell.LEFT or PdfPCell.RIGHT

                sixthtable.addCell(cell78)
                document.add(sixthtable)

                val seventhtable = PdfPTable(2)
                seventhtable.widthPercentage = 100f
                seventhtable.defaultCell.borderColor = BaseColor.BLACK

                val cell79 = phraseText(
                    "Declaration\n" +
                            "We declare that this invoice shows the actual price of the" +
                            " goods described and that all particulars are true and" +
                            " correct."
                )
                cell79.border = PdfPCell.LEFT or PdfPCell.RIGHT or PdfPCell.BOTTOM

                val cell80 =
                    phraseTextRight("for STONEAGE INDUSTRIES\n\n\nAuthorized Signatory")
                cell80.border =
                    PdfPCell.LEFT or PdfPCell.RIGHT or PdfPCell.BOTTOM or PdfPCell.TOP

                seventhtable.addCell(cell79)
                seventhtable.addCell(cell80)
                document.add(seventhtable)

                val eighthtable = PdfPTable(1)
                eighthtable.widthPercentage = 100f
                eighthtable.defaultCell.borderColor = BaseColor.BLACK

                val cell81 =
                    phraseTextCenter("This is a Computer Generated Invoice")
                cell81.border =
                    PdfPCell.NO_BORDER

                eighthtable.addCell(cell81)
                document.add(eighthtable)

                document.close()

                sharePdfThroughWhatsApp(pdfFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun phraseText(text: String): PdfPCell {
        val invoicePhrase = Phrase(text)
        invoicePhrase.font.size = 10f
        val otherCell1 = PdfPCell(invoicePhrase)
        otherCell1.rowspan = 2
        return otherCell1
    }

    private fun phraseTextRightAlign(text: String): PdfPCell {
        val invoicePhrase = Phrase(text)
        invoicePhrase.font.size = 10f
        val otherCell1 = PdfPCell(invoicePhrase)
        otherCell1.horizontalAlignment = Element.ALIGN_RIGHT
        otherCell1.rowspan = 2
        return otherCell1
    }

    private fun fulllengthText(text: String): PdfPCell {
        val invoicePhrase = Phrase(text)
        invoicePhrase.font.size = 10f
        val otherCell1 = PdfPCell(invoicePhrase)
        otherCell1.colspan = 7
        return otherCell1
    }

    private fun pdfCellText(text: String): PdfPCell {
        val cell = PdfPCell(Phrase(text))
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.border = PdfPCell.NO_BORDER
        cell.setPadding(8f)
        cell.isUseAscender = true

        return cell
    }

    private fun phraseTextRight(text: String): PdfPCell {
        val otherCell1 = PdfPCell()
        val invoicePhrase = Paragraph(text)
        invoicePhrase.alignment = Element.ALIGN_RIGHT
        invoicePhrase.font.size = 10f
        otherCell1.addElement(invoicePhrase)
        return otherCell1
    }

    private fun phraseTextRightRowSpan(text: String): PdfPCell {
        val otherCell1 = PdfPCell()
        val invoicePhrase = Paragraph(text)
        invoicePhrase.alignment = Element.ALIGN_RIGHT
        invoicePhrase.font.size = 10f
        otherCell1.rowspan = 2
        otherCell1.addElement(invoicePhrase)
        return otherCell1
    }

    private fun phraseTextRightColSpan(text: String): PdfPCell {
        val otherCell1 = PdfPCell()
        val invoicePhrase = Paragraph(text)
        invoicePhrase.alignment = Element.ALIGN_RIGHT
        invoicePhrase.font.size = 10f
        //otherCell1.colspan = 2
        otherCell1.addElement(invoicePhrase)
        return otherCell1
    }

    private fun phraseTextCenter(text: String): PdfPCell {
        val otherCell1 = PdfPCell()
        val invoicePhrase = Paragraph(text)
        invoicePhrase.alignment = Element.ALIGN_CENTER
        invoicePhrase.font.size = 10f
        otherCell1.addElement(invoicePhrase)
        return otherCell1
    }

    private fun phraseTableInsideCell(text1: String, text2: String, text3: String): PdfPCell {
        val otherCell1 = PdfPCell()
        otherCell1.setPadding(0f)

        val nestedTable = PdfPTable(2)
        nestedTable.widthPercentage = 100f
        nestedTable.defaultCell.borderColor = BaseColor.BLACK

        val firstcell = PdfPCell()
        firstcell.colspan = 2
        val invoicePhrase = Paragraph(text1)
        invoicePhrase.alignment = Element.ALIGN_CENTER
        invoicePhrase.font.size = 10f
        firstcell.addElement(invoicePhrase)

        val secondcell = PdfPCell()
        val secondcellinvoicePhrase = Paragraph(text2)
        secondcellinvoicePhrase.alignment = Element.ALIGN_CENTER
        secondcellinvoicePhrase.font.size = 10f
        secondcell.addElement(secondcellinvoicePhrase)

        val thirdcell = PdfPCell()
        val thirdcellinvoicePhrase = Paragraph(text3)
        thirdcellinvoicePhrase.alignment = Element.ALIGN_CENTER
        thirdcellinvoicePhrase.font.size = 10f
        thirdcell.addElement(thirdcellinvoicePhrase)

        nestedTable.addCell(firstcell)
        nestedTable.addCell(secondcell)
        nestedTable.addCell(thirdcell)

        otherCell1.addElement(nestedTable)

        return otherCell1
    }

    private fun phraseTableInsideCellRow(text1: String, text2: String): PdfPCell {
        val otherCell1 = PdfPCell()
        otherCell1.setPadding(0f)
        val nestedTable = PdfPTable(2)
        nestedTable.widthPercentage = 100f
        nestedTable.defaultCell.borderColor = BaseColor.BLACK

        val firstcell = PdfPCell()
        val firstcellPhrase = Paragraph(text1)
        firstcellPhrase.alignment = Element.ALIGN_CENTER
        firstcellPhrase.font.size = 10f
        firstcell.addElement(firstcellPhrase)

        val secondcell = PdfPCell()
        val secondcellinvoicePhrase = Paragraph(text2)
        secondcellinvoicePhrase.alignment = Element.ALIGN_CENTER
        secondcellinvoicePhrase.font.size = 10f
        secondcell.addElement(secondcellinvoicePhrase)


        nestedTable.addCell(firstcell)
        nestedTable.addCell(secondcell)

        otherCell1.addElement(nestedTable)

        return otherCell1
    }

    private fun createPDF(uri: Uri) {

        val directory = DocumentFile.fromTreeUri(requireActivity(), uri)
        val file = directory?.createFile("application/pdf", "fileName")
        val pfd = requireActivity().contentResolver.openFileDescriptor(file!!.uri, "w")
        val fos = FileOutputStream(pfd!!.fileDescriptor)

        // Create a PDF document with A4 size
        val document = Document(PageSize.A4)

        try {
            // Create a PdfWriter instance
            val writer = PdfWriter.getInstance(document, fos)

            // Open the document for writing
            document.open()

            // Add title
            document.add(Paragraph("Tax Invoice"))

            document.close()

            println("working......")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding?.lifecycleOwner = viewLifecycleOwner
        _binding?.viewModel = mViewModel
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateDateInView()
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString("name", "STONEAGE INDUSTRIES")
        editor.putString("address1", "CHOLA ROAD,PATTANNUR P O")
        editor.putString("address2", "NAYATTUPARA")
        editor.putString("pincode", "670595")
        editor.putString("city", "")
        editor.putString("mobile", "9605252181, 9048133316, 7510252181")
        editor.putString("landline", "0490 2486316")
        editor.putString("gst", "32AEGFS8891H1ZK")
        editor.putString("state", "Kerala")
        editor.putString("statecode", "32")

        editor.putString("ship_name", "STONEAGE INDUSTRIES")
        editor.putString("ship_address1", "CHOLA ROAD,PATTANNUR P O")
        editor.putString("ship_address2", "NAYATTUPARA")
        editor.putString("ship_pincode", "670595")
        editor.putString("city", "")
        editor.putString("ship_mobile", "9605252181, 9048133316, 7510252181")
        editor.putString("ship_landline", "0490 2486316")
        editor.putString("ship_gst", "32AEGFS8891H1ZK")
        editor.putString("ship_state", "Kerala")
        editor.putString("ship_statecode", "32")

        editor.putString("bill_name", "STONEAGE INDUSTRIES")
        editor.putString("bill_address1", "CHOLA ROAD,PATTANNUR P O")
        editor.putString("bill_address2", "NAYATTUPARA")
        editor.putString("bill_pincode", "670595")
        editor.putString("city", "")
        editor.putString("bill_mobile", "9605252181, 9048133316, 7510252181")
        editor.putString("bill_landline", "0490 2486316")
        editor.putString("bill_gst", "32AEGFS8891H1ZK")
        editor.putString("bill_state", "Kerala")
        editor.putString("bill_statecode", "32")

        editor.apply()

        _binding?.btnSubmit?.setOnClickListener {
            if (_binding?.invoice?.text.isNullOrEmpty())
                Toast.makeText(requireActivity(), "Enter Invoice Number", Toast.LENGTH_LONG).show()
            else if (_binding?.name?.text.isNullOrEmpty())
                Toast.makeText(requireActivity(), "Enter Item Name", Toast.LENGTH_LONG).show()
            else if (_binding?.quantity?.text.isNullOrEmpty())
                Toast.makeText(requireActivity(), "Enter Quantity", Toast.LENGTH_LONG).show()
            else if (_binding?.price?.text.isNullOrEmpty())
                Toast.makeText(requireActivity(), "Enter Price", Toast.LENGTH_LONG).show()
            else if (_binding?.rbIgst?.isChecked == false && _binding?.rbSgstCgst?.isChecked == false)
                Toast.makeText(requireActivity(), "Select Tax Type", Toast.LENGTH_LONG).show()
            else if (_binding?.etTax?.text.isNullOrEmpty())
                Toast.makeText(requireActivity(), "Enter Tax Rate", Toast.LENGTH_LONG).show()
            else if (_binding?.hsnsac?.text.isNullOrEmpty())
                Toast.makeText(requireActivity(), "Enter HSN/SAC", Toast.LENGTH_LONG).show()
            else
                writeFileContent()
        }
        _binding?.date?.setOnClickListener {
            showDatePickerDialog()
        }

        _binding?.btnAddress?.setOnClickListener {
            showDialog(1)
        }
        _binding?.btnAddress1?.setOnClickListener {
            showDialog(2)
        }
        _binding?.btnAddress2?.setOnClickListener {
            showDialog(3)
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.maxDate =
            System.currentTimeMillis() // Set max date to the current date
        datePickerDialog.show()
    }

    private val dateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            // Update the EditText with the selected date
            updateDateInView()
        }

    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy" // Choose the date format
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        _binding?.date?.setText(sdf.format(calendar.time))
    }

    fun roundDown(number: Double): Double {
        val down = number.toInt()
        return down.toDouble()
    }

    fun getDecimalValue(number: Double): Double {
        return number - number.toInt()
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }


    companion object {
    }

    private fun openPdf(file: File) {


        // val pdfUri = Uri.fromFile(file)

        val pdfUri = FileProvider.getUriForFile(
            requireActivity(),
            requireActivity().applicationContext.packageName + ".provider",
            file
        )


        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(pdfUri, "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        // Verify that there's an app to handle the intent
        //if (intent.resolveActivity(requireActivity().packageManager) != null) {
        startActivity(intent)
//        } else {
//            Toast.makeText(requireActivity(), "No PDF viewer app found", Toast.LENGTH_SHORT).show()
//        }
    }

    private val CREATE_FILE_REQUEST_CODE = 1

    private fun fileCreate() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_TITLE, "samplepdf.pdf")//${fileName.text}.pdf"
        startActivityForResult(intent, CREATE_FILE_REQUEST_CODE)
    }

    private val REQUEST_PERMISSION_CODE = 123

    private fun requestPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                println("permission granted")
                // Permission already granted, proceed with PDF creation
                writeFileContent()
            }

            else -> {
                writeFileContent()
                println("permission not granted")
                // Request permission
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            writeFileContent()
        } else {
            // PERMISSION NOT GRANTED
        }
    }


    private fun sharePdfThroughWhatsApp(pdfFile: File) {
        // val uri = Uri.fromFile(pdfFile)

        val uri = FileProvider.getUriForFile(
            requireActivity(),
            requireActivity().applicationContext.packageName + ".provider",
            pdfFile
        )


        val whatsappIntent = Intent(Intent.ACTION_SEND)
        whatsappIntent.type = "application/pdf"
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri)
        // whatsappIntent.setPackage("com.whatsapp")
        startActivity(Intent.createChooser(whatsappIntent, "Share To:"))
        println("working....")

//        val intent= Intent()
//        intent.action=Intent.ACTION_SEND
//        intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app:")
//        intent.type="text/plain"
//        startActivity(Intent.createChooser(intent,"Share To:"))

//        try {
//            startActivity(whatsappIntent)
//        } catch (e: Exception) {
//            Toast.makeText(requireActivity(), "WhatsApp not installed", Toast.LENGTH_SHORT).show()
//        }
    }

    fun roundToThreeDecimalPlaces(number: Double): Double {
        val decimalNumber = BigDecimal(number.toString())
        val roundedNumber = decimalNumber.setScale(3, RoundingMode.HALF_UP).toDouble()
        return roundedNumber
    }

//    private fun convertAmountToWords(amount: Double): String {
//        val numberToWords = NumberToWords()
//        val amountString = DecimalFormat("#.##").format(amount)
//        val parts = amountString.split("\\.".toRegex())
//
//        // Convert rupees part to words
//        val rupeesPart = parts[0].toInt()
//        val rupeesInWords = numberToWords.convert(rupeesPart)
//
//        // Convert paise part to words
//        val paisePart = if (parts.size > 1) parts[1].toInt() else 0
//        val paiseInWords = numberToWords.convert(paisePart)
//
//        return "$rupeesInWords Rupees and $paiseInWords Paise"
//    }
//}
//
//class NumberToWords {
//    private val units =
//        arrayOf("", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine")
//    private val teens = arrayOf(
//        "",
//        "Eleven",
//        "Twelve",
//        "Thirteen",
//        "Fourteen",
//        "Fifteen",
//        "Sixteen",
//        "Seventeen",
//        "Eighteen",
//        "Nineteen"
//    )
//    private val tens = arrayOf(
//        "",
//        "Ten",
//        "Twenty",
//        "Thirty",
//        "Forty",
//        "Fifty",
//        "Sixty",
//        "Seventy",
//        "Eighty",
//        "Ninety"
//    )
//
//    fun convert(n: Int): String {
//        return when {
//            n < 10 -> units[n]
//            n < 20 -> teens[n - 11]
//            n < 100 -> tens[n / 10] + " " + convert(n % 10)
//            n < 1000 -> units[n / 100] + " Hundred " + convert(n % 100)
//            else -> ""
//        }
//    }


    fun convertAmountToWords(amount: Double): String {
        val numberFormat = NumberFormat.getInstance(Locale.US)
        val amountStr = numberFormat.format(amount)

        val parts = amountStr.split("\\.".toRegex()).toTypedArray()

        val wholePart = convertNumberToWords(parts[0].toLong())
        val decimalPart = convertNumberToWords(parts[1].toLong())

        val result = "$wholePart Rupees and $decimalPart cents"
        return result.trim()
    }

    fun convertNumberToWords(number: Long): String {
        val units =
            arrayOf("", "thousand", "million", "billion", "trillion", "quadrillion", "quintillion")
        val tens = arrayOf(
            "",
            "ten",
            "twenty",
            "thirty",
            "forty",
            "fifty",
            "sixty",
            "seventy",
            "eighty",
            "ninety"
        )
        val ones = arrayOf(
            "",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "fifteen",
            "sixteen",
            "seventeen",
            "eighteen",
            "nineteen"
        )

        var number = number
        var index = 0
        var words = ""

        do {
            val currentNumber = number % 1000
            if (currentNumber != 0L) {
                val word = convertThreeDigitNumberToWords(currentNumber, tens, ones)
                words = "$word ${units[index]} $words"
            }
            index++
            number /= 1000
        } while (number > 0)

        return words.trim()
    }

    fun convertThreeDigitNumberToWords(
        number: Long,
        tens: Array<String>,
        ones: Array<String>
    ): String {
        val hundred = number / 100
        val remainder = number % 100

        var words = ""

        if (hundred > 0) {
            words += "${ones[hundred.toInt()]} hundred"
            if (remainder > 0) {
                words += " and "
            }
        }

        if (remainder in 1..19) {
            words += ones[remainder.toInt()]
        } else if (remainder >= 20) {
            val tensDigit = remainder / 10
            val onesDigit = remainder % 10
            words += tens[tensDigit.toInt()]
            if (onesDigit > 0) {
                words += " ${ones[onesDigit.toInt()]}"
            }
        }

        return words

    }

//    fun amountToWordsIndianFormat(amount: Double): String {
//        val rupeesInWords = convertToIndianWords(amount.toLong())
//        val paisaInWords = if (amount % 1 != 0.0) {
//            val paisa = (amount % 1) * 100
//            convertToIndianWords(paisa.toLong()) + " Paisa"
//        } else {
//            ""
//        }
//
//        return "$rupeesInWords$paisaInWords Only"
//    }

//    private fun amountToWordsIndianFormat(amount: Double): String {
//        val rupeesInWords = convertToIndianWords(amount.toLong())
//        val paisaInWords = if (amount % 1 != 0.0) {
//            val paisa = (amount % 1) * 100
//            val paisaWords = convertToIndianWords(paisa.toLong())
//            if (paisaWords.isNotEmpty()) " and $paisaWords Paisa" else ""
//        } else {
//            ""
//        }
//
//        return "$rupeesInWords$paisaInWords Only"
//    }

    private fun amountToWordsIndianFormat(amount: Double): String {
        val rupeesInWords = convertToIndianWords(amount.toLong())
        val paisaInWords = if (amount % 1 != 0.0) {
            val paisa = (amount % 1) * 100
            val paisaWords = convertPaisaToWords(paisa.toInt())
            if (paisaWords.isNotEmpty()) " and $paisaWords" else ""
        } else {
            ""
        }

        return "$rupeesInWords$paisaInWords Only"
    }

    private fun convertPaisaToWords(paisa: Int): String {
        val units =
            arrayOf("", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine")
        val teens = arrayOf(
            "",
            "Eleven",
            "Twelve",
            "Thirteen",
            "Fourteen",
            "Fifteen",
            "Sixteen",
            "Seventeen",
            "Eighteen",
            "Nineteen"
        )
        val tens = arrayOf(
            "",
            "Ten",
            "Twenty",
            "Thirty",
            "Forty",
            "Fifty",
            "Sixty",
            "Seventy",
            "Eighty",
            "Ninety"
        )

        return when {
            paisa < 10 -> "Zero " + units[paisa] + " Paisa"
            paisa < 20 -> teens[paisa - 10] + "Paisa"
            else -> tens[paisa / 10] + " " + units[paisa % 10] + " Paisa"
        }
    }

    fun convertToIndianWords(number: Long): String {
        val units =
            arrayOf("", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine")
        val teens = arrayOf(
            "",
            "Eleven",
            "Twelve",
            "Thirteen",
            "Fourteen",
            "Fifteen",
            "Sixteen",
            "Seventeen",
            "Eighteen",
            "Nineteen"
        )
        val tens = arrayOf(
            "",
            "Ten",
            "Twenty",
            "Thirty",
            "Forty",
            "Fifty",
            "Sixty",
            "Seventy",
            "Eighty",
            "Ninety"
        )

        return when {
            number < 10 -> units[number.toInt()]
            number < 20 -> teens[(number - 10).toInt()]
            number < 100 -> "${tens[(number / 10).toInt()]} ${units[(number % 10).toInt()]}"
            number < 1000 -> "${units[(number / 100).toInt()]} Hundred ${convertToIndianWords(number % 100)}"
            number < 100000 -> "${convertToIndianWords(number / 1000)} Thousand ${
                convertToIndianWords(
                    number % 1000
                )
            }"

            number < 10000000 -> "${convertToIndianWords(number / 100000)} Lakh ${
                convertToIndianWords(
                    number % 100000
                )
            }"

            else -> "${convertToIndianWords(number / 10000000)} Crore ${convertToIndianWords(number % 10000000)}"
        }
    }

    private fun showDialog(pos: Int) {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        // dialog.setContentView(R.layout.custom_layout)

        val binding: CustomLayoutBinding = CustomLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        if (pos == 1) {
            binding.etName.setText(sharedPreferences.getString("name", ""))
            binding.etAddress1.setText(sharedPreferences.getString("address1", ""))
            binding.etAddress2.setText(sharedPreferences.getString("address2", ""))
            binding.etZip.setText(sharedPreferences.getString("pincode", ""))
            binding.etMobile.setText(sharedPreferences.getString("mobile", ""))
            binding.etLandline.setText(sharedPreferences.getString("landline", ""))
            binding.etGst.setText(sharedPreferences.getString("gst", ""))
            binding.etState.setText(sharedPreferences.getString("state", ""))
            binding.etStateCode.setText(sharedPreferences.getString("statecode", ""))
        } else if (pos == 2) {
            binding.etName.setText(sharedPreferences.getString("ship_name", ""))
            binding.etAddress1.setText(sharedPreferences.getString("ship_address1", ""))
            binding.etAddress2.setText(sharedPreferences.getString("ship_address2", ""))
            binding.etZip.setText(sharedPreferences.getString("ship_pincode", ""))
            binding.etMobile.setText(sharedPreferences.getString("ship_mobile", ""))
            binding.etLandline.setText(sharedPreferences.getString("ship_landline", ""))
            binding.etGst.setText(sharedPreferences.getString("ship_gst", ""))
            binding.etState.setText(sharedPreferences.getString("ship_state", ""))
            binding.etStateCode.setText(sharedPreferences.getString("ship_statecode", ""))
        } else if (pos == 3) {
            binding.etName.setText(sharedPreferences.getString("bill_name", ""))
            binding.etAddress1.setText(sharedPreferences.getString("bill_address1", ""))
            binding.etAddress2.setText(sharedPreferences.getString("bill_address2", ""))
            binding.etZip.setText(sharedPreferences.getString("bill_pincode", ""))
            binding.etMobile.setText(sharedPreferences.getString("bill_mobile", ""))
            binding.etLandline.setText(sharedPreferences.getString("bill_landline", ""))
            binding.etGst.setText(sharedPreferences.getString("bill_gst", ""))
            binding.etState.setText(sharedPreferences.getString("bill_state", ""))
            binding.etStateCode.setText(sharedPreferences.getString("bill_statecode", ""))
        }

        binding.btnSubmit.setOnClickListener {


            val editor = sharedPreferences.edit()

            if (pos == 1) {
                editor.putString("name", binding.etName.text.toString())
                editor.putString("address1", binding.etAddress1.text.toString())
                editor.putString("address2", binding.etAddress2.text.toString())
                editor.putString("city", binding.etCity.text.toString())
                editor.putString("pincode", binding.etZip.text.toString())
                editor.putString("mobile", binding.etMobile.text.toString())
                editor.putString("landline", binding.etLandline.text.toString())
                editor.putString("gst", binding.etGst.text.toString())
                editor.putString("state", binding.etState.text.toString())
                editor.putString("statecode", binding.etStateCode.text.toString())

                var address = binding.etName.text.toString() + "\n" +
                        binding.etAddress1.text.toString() + "\n" +
                        binding.etAddress2.text.toString() + "\n" +
                        binding.etCity.text.toString() + "\n" +
                        binding.etZip.text.toString() + "\n" +
                        "Mob: " + binding.etMobile.text.toString() + "\n" +
                        binding.etLandline.text.toString() + "\n" +
                        "GSTIN/UIN: " + binding.etGst.text.toString() + "\n" +
                        "State Name: " + binding.etState.text.toString() + "\n" +
                        "Code: " + binding.etStateCode.text.toString().trimIndent()
                _binding?.address?.setText(removeExtraLineSpaces(address))
            }

            if (pos == 2) {
                editor.putString("ship_name", binding.etName.text.toString())
                editor.putString("ship_address1", binding.etAddress1.text.toString())
                editor.putString("ship_address2", binding.etAddress2.text.toString())
                editor.putString("city", binding.etCity.text.toString())
                editor.putString("ship_pincode", binding.etZip.text.toString())
                editor.putString("ship_mobile", binding.etMobile.text.toString())
                editor.putString("ship_landline", binding.etLandline.text.toString())
                editor.putString("ship_gst", binding.etGst.text.toString())
                editor.putString("ship_state", binding.etState.text.toString())
                editor.putString("ship_statecode", binding.etStateCode.text.toString())


                var address = "Consignee (Ship to)" + "\n" + binding.etName.text.toString() + "\n" +
                        binding.etAddress1.text.toString() + "\n" +
                        binding.etAddress2.text.toString() + "\n" +
                        binding.etCity.text.toString() + "\n" +
                        binding.etZip.text.toString() + "\n" +
                        "Mob: " + binding.etMobile.text.toString() + "\n" +
                        binding.etLandline.text.toString() + "\n" +
                        "GSTIN/UIN: " + binding.etGst.text.toString() + "\n" +
                        "State Name: " + binding.etState.text.toString() + "\n" +
                        "Code: " + binding.etStateCode.text.toString().trimIndent()
                _binding?.address1?.setText(removeExtraLineSpaces(address))


                if (_binding?.address2?.text.toString().isEmpty()) {
                    editor.putString("bill_name", binding.etName.text.toString())
                    editor.putString("bill_address1", binding.etAddress1.text.toString())
                    editor.putString("bill_address2", binding.etAddress2.text.toString())
                    editor.putString("city", binding.etCity.text.toString())
                    editor.putString("bill_pincode", binding.etZip.text.toString())
                    editor.putString("bill_mobile", binding.etMobile.text.toString())
                    editor.putString("bill_landline", binding.etLandline.text.toString())
                    editor.putString("bill_gst", binding.etGst.text.toString())
                    editor.putString("bill_state", binding.etState.text.toString())
                    editor.putString("bill_statecode", binding.etStateCode.text.toString())


                    var address = "Buyer (Bill to)" + "\n" + binding.etName.text.toString() + "\n" +
                            binding.etAddress1.text.toString() + "\n" +
                            binding.etAddress2.text.toString() + "\n" +
                            binding.etCity.text.toString() + "\n" +
                            binding.etZip.text.toString() + "\n" +
                            "Mob: " + binding.etMobile.text.toString() + "\n" +
                            binding.etLandline.text.toString() + "\n" +
                            "GSTIN/UIN: " + binding.etGst.text.toString() + "\n" +
                            "State Name: " + binding.etState.text.toString() + "\n" +
                            "Code: " + binding.etStateCode.text.toString().trimIndent()

                    _binding?.address2?.setText(removeExtraLineSpaces(address))
                }
            }

            if (pos == 3) {
                editor.putString("bill_name", binding.etName.text.toString())
                editor.putString("bill_address1", binding.etAddress1.text.toString())
                editor.putString("bill_address2", binding.etAddress2.text.toString())
                editor.putString("city", binding.etCity.text.toString())
                editor.putString("bill_pincode", binding.etZip.text.toString())
                editor.putString("bill_mobile", binding.etMobile.text.toString())
                editor.putString("bill_landline", binding.etLandline.text.toString())
                editor.putString("bill_gst", binding.etGst.text.toString())
                editor.putString("bill_state", binding.etState.text.toString())
                editor.putString("bill_statecode", binding.etStateCode.text.toString())


                var address = "Buyer (Bill to)" + "\n" + binding.etName.text.toString() + "\n" +
                        binding.etAddress1.text.toString() + "\n" +
                        binding.etAddress2.text.toString() + "\n" +
                        binding.etCity.text.toString() + "\n" +
                        binding.etZip.text.toString() + "\n" +
                        "Mob: " + binding.etMobile.text.toString() + "\n" +
                        binding.etLandline.text.toString() + "\n" +
                        "GSTIN/UIN: " + binding.etGst.text.toString() + "\n" +
                        "State Name: " + binding.etState.text.toString() + "\n" +
                        "Code: " + binding.etStateCode.text.toString().trimIndent()

                _binding?.address2?.setText(removeExtraLineSpaces(address))
            }

            editor.apply()
            dialog.dismiss()
        }

        dialog.show()
    }

    fun removeExtraLineSpaces(inputText: String): String {
        // Use a regular expression to replace multiple consecutive line breaks with a single line break
        val regex = Regex("\\n{2,}")
        return inputText.replace(regex, "\n")
    }

    fun calculateTaxAmount(totalAmount: Double, taxRate: Double): Double {
        val taxAmount = totalAmount - (totalAmount / (1 + taxRate / 100))
        return taxAmount
    }

    fun calculateNetAmount(totalAmount: Double, taxRate: Double?): Double {
        if (taxRate != null) {
            return totalAmount / (1 + taxRate / 100)
        }
        return 0.0
    }
}

