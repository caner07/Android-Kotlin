package com.canerkaya.ortalamahesapla

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.yeni_ders_layout.view.*
import kotlinx.android.synthetic.main.yeni_ders_layout.view.editYeniDersAdi

class MainActivity : AppCompatActivity() {

    private val DERSLER= arrayOf("Matematik","Türkçe","Fizik","Algoritma","Mobil")
    private var tumDerslerBilgiler:ArrayList<Dersler> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var adapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,DERSLER)
        editDersAdi.setAdapter(adapter)

        if (rootLayout.childCount==0){
            btnHesapla.visibility=View.INVISIBLE
        }else{
            btnHesapla.visibility=View.VISIBLE
        }

        btnDersEkle.setOnClickListener {
            if (!editDersAdi.text.isNullOrEmpty()){
                var inflater=layoutInflater
                var yeniDersView= inflater.inflate(R.layout.yeni_ders_layout,null)
                yeniDersView.editYeniDersAdi.setAdapter(adapter)
                var dersAdi=editDersAdi.text.toString()
                var dersKredi=spnDersKredi.selectedItem.toString()
                var dersNot=spnDersNotu.selectedItem.toString()
                yeniDersView.editYeniDersAdi.setText(dersAdi)
                yeniDersView.spnYeniDersKredi.setSelection(getSpinnerIndex(spnDersKredi,dersKredi))
                yeniDersView.spnYeniDersNotu.setSelection(getSpinnerIndex(spnDersNotu,dersNot))


                yeniDersView.btnDersSil.setOnClickListener {
                    rootLayout.removeView(yeniDersView)
                    if (rootLayout.childCount==0){
                        btnHesapla.visibility=View.INVISIBLE
                    }else{
                        btnHesapla.visibility=View.VISIBLE
                    }
                }
                rootLayout.addView(yeniDersView,0)
                btnHesapla.visibility=View.VISIBLE
                sifirla()
            }else{
                TastyToast.makeText(this,"Ders Adını Boş Bırakamazsınız!",TastyToast.LENGTH_LONG,TastyToast.WARNING)
            }
            }
    }

     fun sifirla() {
         editDersAdi.setText("")
         spnDersKredi.setSelection(0)
         spnDersNotu.setSelection(0)
    }

    fun getSpinnerIndex(spinner: Spinner,arananDeger:String) : Int{
        var index=0
        for (i in 0..spinner.count){
            if (spinner.getItemAtPosition(i).toString().equals(arananDeger)){
                index=i
                break
            }
        }
        return index
    }
    fun ortalamaHesapla(view: View) {
        var toplamNot=0.0
        var toplamKredi=0.0
        for (i in 0..(rootLayout.childCount-1)){
            var  tekSatir=rootLayout.getChildAt(i)
            var geciciDers=Dersler(tekSatir.editYeniDersAdi.text.toString(),
                ((tekSatir.spnYeniDersKredi.selectedItemPosition)+1).toString(),
                tekSatir.spnYeniDersNotu.selectedItem.toString())
            tumDerslerBilgiler.add(geciciDers)
        }
        for (oAnkiDers in tumDerslerBilgiler){
            toplamNot+=harfiNotaCevir(oAnkiDers.dersHarfNotu)*(oAnkiDers.dersKredi.toDouble())
            toplamKredi+=oAnkiDers.dersKredi.toDouble()
        }
        var ortalama=toplamNot/toplamKredi
        if (ortalama>=1.8){
            TastyToast.makeText(this,"Not Ortalaman = " + ortalama,TastyToast.LENGTH_LONG,TastyToast.SUCCESS)
        }else{
            TastyToast.makeText(this,"Not Ortalaman = " + ortalama,TastyToast.LENGTH_LONG,TastyToast.ERROR)
        }


        tumDerslerBilgiler.clear()
    }

    fun harfiNotaCevir(harfNotu:String):Double{
        var deger=0.0
        when(harfNotu){
            "AA"->deger=4.0
            "BA"->deger=3.5
            "BB"->deger=3.0
            "CB"->deger=2.5
            "CC"->deger=2.0
            "DC"->deger=1.5
            "DD"->deger=1.0
            "FD"->deger=0.5
            "FF"->deger=0.0
        }
        return deger
    }
}
