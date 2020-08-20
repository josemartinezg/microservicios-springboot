import { NgModule } from '@angular/core';
import { DemoContentComponent } from 'app/modules/admin/ui/content-layouts/common/demo-content/demo-content.component';

@NgModule({
    declarations: [
        DemoContentComponent
    ],
    exports     : [
        DemoContentComponent
    ]
})
export class DemoContentModule
{
}
