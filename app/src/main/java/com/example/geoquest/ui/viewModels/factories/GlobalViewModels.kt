package com.example.geoquest.ui.viewModels.factories

import com.example.geoquest.ui.viewModels.InventoryReloaderViewModel
import com.example.geoquest.ui.viewModels.InventoryViewModel
import com.example.geoquest.ui.viewModels.NavBarViewModel

object GlobalViewModels {
    val navBarViewModel = NavBarViewModel()
    val inventoryReloader = InventoryReloaderViewModel()
    val inventoryHandler = InventoryViewModel()
}