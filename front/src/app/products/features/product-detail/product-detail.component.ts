import { NgIf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Product } from 'app/products/data-access/product.model';
import { ProductsService } from 'app/products/data-access/products.service';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [RouterModule,NgIf],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent {

  //product: any;

  @Input() product!: Product;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductsService
  ) {}


  ngOnInit(): void {
    const productId = this.route.snapshot.paramMap.get('id');
    if (productId) {
      this.productService.get().subscribe((products) => {
        this.product = products.filter((p)=>p.id == Number(productId))[0];
        console.log(this.product);
      });
    }
  }

  addToCart(product: Product) {
    this.productService.addToCartShopping(product);
    alert(`${product.name} ajouté au panier !`);
  }
}
