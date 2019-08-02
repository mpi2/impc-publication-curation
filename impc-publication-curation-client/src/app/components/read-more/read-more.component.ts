import { Component, Input, ElementRef, OnChanges, OnInit } from '@angular/core';

@Component({
  selector: 'impc-read-more',
  template: `
    <div [innerHTML]="currentText | highlightText: keyword:'default'"></div>
    <a
      mat-button
      *ngIf="!hideToggle"
      (click)="toggleView()"
      color="accent"
      >Read {{ isCollapsed ? 'more' : 'less' }}</a
    >
  `
})
export class ReadMoreComponent implements OnChanges {
  @Input() text: string;
  @Input() keyword: string;
  @Input() maxLength = 100;
  currentText: string;
  hideToggle = true;

  public isCollapsed = true;

  constructor(private elementRef: ElementRef) {}

  toggleView() {
    this.isCollapsed = !this.isCollapsed;
    this.determineView();
  }
  determineView() {
    if (
      !this.text ||
      this.text.replace(/<\/?[^>]+(>|$)/g, '').length <= this.maxLength
    ) {
      this.currentText = this.text;
      this.isCollapsed = false;
      this.hideToggle = true;
      return;
    }
    this.hideToggle = false;
    if (this.isCollapsed === true) {
      this.currentText = this.text.substring(0, this.maxLength) + '...';
    } else if (this.isCollapsed === false) {
      this.currentText = this.text;
    }
  }

  ngOnChanges() {
    this.determineView();
  }
}
