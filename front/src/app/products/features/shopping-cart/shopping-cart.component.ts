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

  items: Product[] = [];

  constructor(private productService: ProductsService) {}

  ngOnInit() {
    this.items = this.productService.getItemsProducts();
  }

  removeItem(product: Product) {
    this.productService.removeFromCart(product);
    this.items = this.productService.getItemsProducts(); 
  }
}
