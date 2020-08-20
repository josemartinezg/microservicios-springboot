import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { SharedModule } from 'app/shared/shared.module';
import { FormsFieldsComponent } from 'app/modules/admin/ui/forms/fields/fields.component';
import { formsFieldsRoutes } from 'app/modules/admin/ui/forms/fields/fields.routing';

@NgModule({
    declarations: [
        FormsFieldsComponent
    ],
    imports     : [
        RouterModule.forChild(formsFieldsRoutes),
        MatButtonModule,
        MatDividerModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatMenuModule,
        SharedModule
    ]
})
export class FormsFieldsModule
{
}
