import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InfrafrontierAlleleListComponent } from './infrafrontier-allele-list.component';

describe('InfrafrontierAlleleListComponent', () => {
  let component: InfrafrontierAlleleListComponent;
  let fixture: ComponentFixture<InfrafrontierAlleleListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InfrafrontierAlleleListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfrafrontierAlleleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
