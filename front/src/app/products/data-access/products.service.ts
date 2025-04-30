import { Injectable, inject, signal } from "@angular/core";
import { Product } from "./product.model";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, catchError, Observable, of, tap } from "rxjs";

@Injectable({
    providedIn: "root"
}) export class ProductsService {

    private readonly http = inject(HttpClient);
    private readonly path = "/api/products";

    private items: Product[] = [];

    private readonly _products = signal<Product[]>([]);

    public readonly products = this._products.asReadonly();

    private cartItemCount = new BehaviorSubject<number>(0);

    cartCount$ = this.cartItemCount.asObservable(); 


    addToCartShopping(product: Product) {
        this.items.push(product);
        this.updateCount();
      }
    getItemsProducts(): Product[] {
        return this.items;
      }
    
      clearCartShopping() {
        this.items = [];
        this.updateCount();
        return this.items;
      }

      removeFromCart(product: Product) {
        const index = this.items.findIndex(p => p.id === product.id);
        if (index !== -1) {
          this.items.splice(index, 1);
          this.updateCount();
        }
      }
      private updateCount() {
        this.cartItemCount.next(this.items.length);
      }

    public get(): Observable<Product[]> {
        return this.http.get<Product[]>(this.path).pipe(
            catchError((error) => {
                return this.http.get<Product[]>("assets/products.json");
            }),
            tap((products) => this._products.set(products)),
        );
    }

    public create(product: Product): Observable<boolean> {
        return this.http.post<boolean>(this.path, product).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._products.update(products => [product, ...products])),
        );
    }

    public update(product: Product): Observable<boolean> {
        return this.http.patch<boolean>(`${this.path}/${product.id}`, product).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._products.update(products => {
                return products.map(p => p.id === product.id ? product : p)
            })),
        );
    }

    public delete(productId: number): Observable<boolean> {
        return this.http.delete<boolean>(`${this.path}/${productId}`).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._products.update(products => products.filter(product => product.id !== productId))),
        );
    }
}