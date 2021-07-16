package com.example.computershopsystemadmin.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.MoreCategoryFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class MoreCategoryFragment extends Fragment {
    MoreCategoryFragmentBinding binding;
    GridView gridProduct;
    ProductManagementFragment productManagementFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragTransaction;
    Bundle result;
    MoreCategoryFragment(GridView gridProduct) {
        this.gridProduct = gridProduct;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = MoreCategoryFragmentBinding.inflate(getLayoutInflater());
        productManagementFragment = new ProductManagementFragment();
        result = new Bundle();
        fragmentManager = getActivity().getSupportFragmentManager();
        fragTransaction = fragmentManager.beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragTransaction.addToBackStack(null);
        InitializeButton();
        View view = binding.getRoot();
        return view;
    }

    void InitializeButton() {
        buttonHP();
        buttonLenovo();
        buttonAcer();
        buttonAsus();
        buttonDell();
        buttonLowPrice();
        buttonMediumPrice();
        buttonHighPrice();
        button512GBRom();
        button1TBRom();
        button2TBRom();
        button4GBRam();
        button8GBRam();
        button16GBRam();
        button14ScreenSize();
        button156ScreenSize();
        button17ScreenSize();
        buttonI3();
        buttonI5();
        buttonI7();
    }

    public void PassData(String filter) {
        result.putString("Filter", filter);
        productManagementFragment.setArguments(result);
        fragTransaction.replace(R.id.fl_wrapper, productManagementFragment);
        fragTransaction.commit();
    }
    public void buttonHP() {
        binding.ibtnHp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("HP");
            }
        });
    }

    public void buttonDell() {
        binding.ibtnDell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("Dell");
            }
        });
    }

    public void buttonAcer() {
        binding.ibtnAcer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("Acer");
            }
        });
    }

    public void buttonAsus() {
        binding.ibtnAsus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("Asus");
            }
        });
    }

    public void buttonLenovo() {
        binding.ibtnLenovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("Lenovo");
            }
        });
    }

    public void buttonLowPrice() {
        binding.btnLowPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("LowPrice");
            }
        });
    }

    public void buttonMediumPrice() {
        binding.btnMediumPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("MediumPrice");
            }
        });
    }

    public void buttonHighPrice() {
        binding.btnHighPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("HighPrice");
            }
        });
    }

    public void button4GBRam() {
        binding.btn4gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("4gb");
            }
        });
    }

    public void button8GBRam() {
        binding.btn8gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("8gb");
            }
        });
    }

    public void button16GBRam() {
        binding.btn16gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("16gb");
            }
        });
    }

    public void button512GBRom() {
        binding.btn512gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("512gb");
            }
        });
    }

    public void button1TBRom() {
        binding.btn1tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("1tb");
            }
        });
    }

    public void button2TBRom() {
        binding.btn2tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("2tb");
            }
        });
    }

    public void buttonI3() {
        binding.btni3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("i3");
            }
        });
    }

    public void buttonI5() {
        binding.btni5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("i5");
            }
        });
    }

    public void buttonI7() {
        binding.btni7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("i7");
            }
        });
    }

    public void button14ScreenSize() {
        binding.btnScreen14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("s14");
            }
        });
    }

    public void button156ScreenSize() {
        binding.btnScreen156.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("s15.6");
            }
        });
    }

    public void button17ScreenSize() {
        binding.btnScreen17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassData("s17");
            }
        });
    }
}