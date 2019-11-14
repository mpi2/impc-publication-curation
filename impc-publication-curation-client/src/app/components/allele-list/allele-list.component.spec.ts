import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { AlleleListComponent } from "./allele-list.component";

describe("AlleleListComponent", () => {
  let component: AlleleListComponent;
  let fixture: ComponentFixture<AlleleListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AlleleListComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlleleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
