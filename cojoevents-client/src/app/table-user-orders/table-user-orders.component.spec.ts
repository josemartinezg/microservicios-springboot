import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TableUserOrdersComponent } from './table-user-orders.component';

describe('TableUserOrdersComponent', () => {
  let component: TableUserOrdersComponent;
  let fixture: ComponentFixture<TableUserOrdersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TableUserOrdersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableUserOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
