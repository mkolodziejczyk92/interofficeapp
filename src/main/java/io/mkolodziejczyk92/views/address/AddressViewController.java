package io.mkolodziejczyk92.views.address;

import io.mkolodziejczyk92.data.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class AddressViewController {

    private AddressesView addressesView;
    private NewAddressFormView newAddressFormView;
    private AddressService addressService;

}
