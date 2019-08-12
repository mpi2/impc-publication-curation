import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderIdListComponent } from './order-id-list.component';

describe('OrderIdListComponent', () => {
  let component: OrderIdListComponent;
  let fixture: ComponentFixture<OrderIdListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderIdListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderIdListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
