import { ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormControl } from '@angular/forms';
import { BehaviorSubject, combineLatest, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { IconsService } from 'app/modules/admin/ui/icons/icons.service';

@Component({
    selector       : 'icons',
    templateUrl    : './icons.component.html',
    styleUrls      : ['./icons.component.scss'],
    encapsulation  : ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class IconsComponent implements OnInit
{
    icons$: Observable<any>;
    iconSizeInput: FormControl;
    filteredIcons$: Observable<any>;
    filterValue$: BehaviorSubject<any>;

    /**
     * Constructor
     *
     * @param {IconsService} _iconsService
     */
    constructor(
        private _iconsService: IconsService
    )
    {
        // Set the defaults
        this.filterValue$ = new BehaviorSubject('');
        this.iconSizeInput = new FormControl('24');
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    ngOnInit(): void
    {
        this.icons$ = this._iconsService.icons;

        // Create filtered icons
        this.filteredIcons$ = combineLatest([this.icons$, this.filterValue$])
            .pipe(
                map(([icons, filterValue]) => {

                    // Filter the icons
                    const filteredIcons = icons.list.filter(icon => icon.toLowerCase().includes(filterValue.toLowerCase()));

                    // Update the list with the filtered icons
                    return {
                        ...icons,
                        list: filteredIcons
                    };
                })
            );
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Filter icons
     *
     * @param event
     */
    filterIcons(event): void
    {
        // Push the value to the observable
        this.filterValue$.next(event.target.value);
    }
}
