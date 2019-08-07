import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicationAdderComponent } from './publication-adder.component';

describe('PublicationAdderComponent', () => {
  let component: PublicationAdderComponent;
  let fixture: ComponentFixture<PublicationAdderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PublicationAdderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PublicationAdderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
