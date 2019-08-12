import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmmaIdListComponent } from './emma-id-list.component';

describe('EmmaIdListComponent', () => {
  let component: EmmaIdListComponent;
  let fixture: ComponentFixture<EmmaIdListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmmaIdListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmmaIdListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
