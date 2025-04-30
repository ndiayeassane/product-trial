import { NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Product } from 'app/products/data-access/product.model';
import { ProductsService } from 'app/products/data-access/products.service';

@Component({
  selector: 'app-shopping-cart',
  standalone: true,
  imports: [NgIf,NgFor],
  templateUrl: './shopping-cart.component.html',
  styleUrl: './shopping-cart.component.css'
})
export class ShoppingCartComponent implements OnInit{

  // on declare un tableau de produit vide
  items: Product[] = [];

  constructor(private productService: ProductsService) {}

  ngOnInit() {
    /**
     * on recupere tous les produits ajoutes dans le panier
     */
    this.items = this.productService.getItemsProducts();
  }

  /**
   * On supprime un produit et on met a jour la liste des produit dans le panier
   * @param product 
   */
  removeItem(product: Product) {
    this.productService.removeFromCart(product);
    this.items = this.productService.getItemsProducts(); 
  }
}
