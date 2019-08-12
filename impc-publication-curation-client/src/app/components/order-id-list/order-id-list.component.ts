import { Component, OnInit, Input, Output } from '@angular/core';
import { Publication } from 'src/app/shared/models/publication.model';
import {MatChipInputEvent} from '@angular/material/chips';
import {COMMA, ENTER} from '@angular/cdk/keycodes';

@Component({
  selector: 'impc-order-id-list',
  templateUrl: './order-id-list.component.html',
  styleUrls: ['./order-id-list.component.scss']
})
export class OrderIdListComponent implements OnInit {

  @Input()
  get publication() {
    return this.publicationValue;
  }

  set publication(val) {
    this.publicationValue = val;
  }

  publicationValue: Publication;

  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];

  constructor() { }

  ngOnInit() {
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if ((value || '').trim()) {
      this.publicationValue.orderIds.push(value.trim());
    }

    if (input) {
      input.value = '';
    }
  }

  remove(orderId: string): void {
    const index = this.publicationValue.orderIds.indexOf(orderId);

    if (index >= 0) {
      this.publicationValue.orderIds.splice(index, 1);
    }
  }

}
