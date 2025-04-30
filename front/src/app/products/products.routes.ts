import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { ProductListComponent } from "./features/product-list/product-list.component";
import { ProductDetailComponent } from "./features/product-detail/product-detail.component";
import { ShoppingCartComponent } from "./features/shopping-cart/shopping-cart.component";

export const PRODUCTS_ROUTES: Routes = [
	{
		path: "list",
		component: ProductListComponent,
	},
	{ 	path: ':id', 
		component: ProductDetailComponent 
	},

	{ path: "**", redirectTo: "list" },
];
