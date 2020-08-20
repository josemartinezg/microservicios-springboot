import { Route } from '@angular/router';
import { IconsComponent } from 'app/modules/admin/ui/icons/icons.component';
import { IconsResolver } from 'app/modules/admin/ui/icons/icons.resolvers';

export const iconRoutes: Route[] = [
    {
        // Redirect /icons to /icons/material-twotone
        path      : 'icons',
        redirectTo: 'icons/material-twotone',
        pathMatch : 'full'
    },
    {
        path     : '**',
        component: IconsComponent,
        resolve  : {
            icons: IconsResolver
        }
    }
];
