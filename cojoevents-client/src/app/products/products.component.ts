import { Component, OnInit } from '@angular/core';
import { IPayPalConfig, ICreateOrderRequest } from 'ngx-paypal';
import { Producto } from 'app/models/producto';
import { ProductoService } from 'app/services/producto.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  productos : Producto[] = [];
  precio = 0;
  producto : Producto;
  public payPalConfig ? : IPayPalConfig;  
  constructor(private productoService : ProductoService) { }
  
  ngOnInit() {
    this.initConfig();
    return this.productoService
    .obtenerAllProductos()
    .subscribe(productoResponse => this.productos = productoResponse)
    
  }

  private initConfig(): void {
    this.payPalConfig = {
        currency: 'USD',
        //Revisar documentación
        clientId: 'sb',
        createOrderOnClient: (data) => < ICreateOrderRequest > {
            intent: 'CAPTURE',
            purchase_units: [{
                amount: {
                    currency_code: 'USD',
                    value: `${this.producto.costo}`,
                    breakdown: {
                        item_total: {
                            currency_code: 'USD',
                            value: `${this.producto.costo}`
                        }
                    }
                },
                items: [{
                    name: `${this.producto.nombreProducto}`,
                    quantity: '1',
                    category: 'DIGITAL_GOODS',
                    unit_amount: {
                        currency_code: 'USD',
                        value: `${this.producto.costo}`,
                    },
                }]
            }]
        },
        advanced: {
            commit: 'true'
        },
        style: {
            label: 'paypal',
            layout: 'vertical'
        },
        onApprove: (data, actions) => {
            console.log('onApprove - transaction was approved, but not authorized', data, actions);
            actions.order.get().then(details => {
                console.log('onApprove - you can get full order details inside onApprove: ', details);
            });

        },
        onClientAuthorization: (data) => {
            console.log('onClientAuthorization - you should probably inform your server about completed transaction at this point', data);
        
        },
        onCancel: (data, actions) => {
            console.log('OnCancel', data, actions);

        },
        onError: err => {
            console.log('OnError', err);
        },
        onClick: (data, actions) => {
            console.log('onClick', data, actions);
        },
    };
}

}
