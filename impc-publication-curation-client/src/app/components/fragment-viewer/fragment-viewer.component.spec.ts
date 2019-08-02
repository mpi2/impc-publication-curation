import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FragmentViewerComponent } from './fragment-viewer.component';

describe('FragmentViewerComponent', () => {
  let component: FragmentViewerComponent;
  let fixture: ComponentFixture<FragmentViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FragmentViewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FragmentViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
