import { Component, OnInit } from '@angular/core';
import { IPayPalConfig, ICreateOrderRequest } from 'ngx-paypal';
import { Producto } from 'app/models/producto';
import { Compra } from 'app/models/compra';
import { ProductoService } from 'app/services/producto.service';
import { CompraService } from 'app/services/compra.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  productos : Producto[] = [];
  precio = 0;
  compra : Compra;
  producto : Producto;
  public payPalConfig ? : IPayPalConfig;  
  constructor(private productoService : ProductoService, private compraService : CompraService) { }
  
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
        clientId: 'AZlizNGpR2cB8eI5ekmqmTCbpA1oNyGcMf_olOszbAI7_PkUyv08MCqLc50Ed4upaUQSCIq2JwA98U9D',
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
            layout: 'vertical',
            size: 'small',
            color: 'blue',
            shape: 'rect'
        },
        onApprove: (data, actions) => {
            console.log('onApprove - transaction was approved, but not authorized', data, actions);
            actions.order.get().then(details => {
                console.log('onApprove - you can get full order details inside onApprove: ', details);
            });

        },
        onClientAuthorization: (data) => {
            console.log('onClientAuthorization - you should probably inform your server about completed transaction at this point', data);
            const venta = {
              monto : this.producto.costo,
              //Configurar con login
              usuario : "chema",
              producto : this.producto.nombreProducto
            }; 
          
          this.compraService
          .realizarCompra(venta)
          .subscribe(productResponse => {this.compra = productResponse;});

          this.compraService
          .pedirImpresion()
          .subscribe();
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
