import { Component, OnInit, Input, Output } from '@angular/core';
import { Publication } from 'src/app/shared/models/publication.model';
import {MatChipInputEvent} from '@angular/material/chips';
import {COMMA, ENTER} from '@angular/cdk/keycodes';

@Component({
  selector: 'impc-emma-id-list',
  templateUrl: './emma-id-list.component.html',
  styleUrls: ['./emma-id-list.component.scss']
})
export class EmmaIdListComponent implements OnInit {

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
      this.publicationValue.emmaIds.push(value.trim());
    }

    if (input) {
      input.value = '';
    }
  }

  remove(emmaId: string): void {
    const index = this.publicationValue.emmaIds.indexOf(emmaId);

    if (index >= 0) {
      this.publicationValue.emmaIds.splice(index, 1);
    }
  }

}

