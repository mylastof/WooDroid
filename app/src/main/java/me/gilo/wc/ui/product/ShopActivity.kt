package me.gilo.wc.ui.product

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.widget.Toast
import com.miguelcatalan.materialsearchview.MaterialSearchView
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.content_shop.*
import me.gilo.wc.R
import me.gilo.wc.adapter.ProductAdapter
import me.gilo.wc.common.BaseActivity
import me.gilo.wc.common.Status
import me.gilo.wc.ui.state.ProgressDialogFragment
import me.gilo.wc.viewmodels.ProductViewModel
import me.gilo.woodroid.models.Product
import org.json.JSONObject
import java.util.*
import android.text.Editable
import android.text.TextWatcher


class ShopActivity : BaseActivity() {

    lateinit var adapter: ProductAdapter
    lateinit var products: ArrayList<Product>

    lateinit var viewModel: ProductViewModel
    val TAG = this::getLocalClassName

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        setSupportActionBar(toolbar)

        viewModel = getViewModel(ProductViewModel::class.java)

        title = "Shop"

        val layoutManager = GridLayoutManager(baseContext, 2)
        rvShop.layoutManager = layoutManager
        rvShop.isNestedScrollingEnabled = false

        products = ArrayList()

        adapter = ProductAdapter(products)
        rvShop.adapter = adapter

        products()

        etSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                   search(s.toString())
                }else{
                    products()
                }
            }
        })
    }

    private fun search(query : String) {
        viewModel.search(query).observe(this, android.arch.lifecycle.Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    products.clear()

                    val productsResponse = response.data()
                    for (product in productsResponse) {
                        products.add(product)
                    }

                    adapter.notifyDataSetChanged()

                }

                Status.ERROR -> {


                }

                Status.EMPTY -> {

                }
            }

        })

    }

    private fun products() {
        viewModel.products().observe(this, android.arch.lifecycle.Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    products.clear()
                    val productsResponse = response.data()
                    for (product in productsResponse) {
                        products.add(product)
                    }

                    adapter.notifyDataSetChanged()

                }

                Status.ERROR -> {

                }

                Status.EMPTY -> {

                }
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.products, menu)

        return true
    }

    private lateinit var progressDialog: ProgressDialogFragment

    fun showLoading(title: String, message: String) {
        val manager = supportFragmentManager
        progressDialog = ProgressDialogFragment.newInstance(title, message)
        progressDialog.isCancelable = false
        progressDialog.show(manager, "progress")
    }

    fun showLoading() {
        showLoading("This will only take a sec", "Loading")
    }

    fun stopShowingLoading() {
        progressDialog.dismiss()
    }

}