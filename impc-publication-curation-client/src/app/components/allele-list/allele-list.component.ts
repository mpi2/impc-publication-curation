import { AlleleAutocompleteService } from './../../shared/services/allele-autocomplete.service';
import { Component, OnInit, Input } from '@angular/core';
import { ENTER, SEMICOLON } from '@angular/cdk/keycodes';
import { Observable } from 'rxjs';
import { FormControl } from '@angular/forms';
import { startWith, mergeMap } from 'rxjs/operators';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';

@Component({
  selector: 'impc-allele-list',
  templateUrl: './allele-list.component.html',
  styleUrls: ['./allele-list.component.scss']
})
export class AlleleListComponent implements OnInit {
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  options = [];
  @Input()
  editable = false;
  private _alleles = [];

  filteredOptions: Observable<any[]>;
  myControl: FormControl = new FormControl();

  @Input()
  set alleles(alleles) {
    this._alleles = alleles;
  }

  get alleles(): Array<any> {
    return this._alleles;
  }

  separatorKeysCodes = [ENTER, SEMICOLON];

  constructor(private allelesService: AlleleAutocompleteService) {}

  ngOnInit() {
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      mergeMap(val => {
        val = typeof val === 'string' ? val : '';
        return this.filter(val);
      })
    );
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;
    if ((value || '').trim() && this.options.indexOf(value.trim())) {
      this._alleles.push(value.trim());
    }

    if (input) {
      input.value = '';
    }
  }

  addFromAutcomplete(event: MatAutocompleteSelectedEvent, textInput): void {
    const value = event.option.value;
    if (value) {
      this.myControl.setValue('');
      textInput.value = '';
      this._alleles.push(value);
    }
  }

  remove(allele: any, textInput): void {
    const index = this._alleles.indexOf(allele);

    if (index >= 0) {
      this._alleles.splice(index, 1);
      this.myControl.setValue('');
    }
  }

  filter(val: string) {
    return this.allelesService.getAlleles(val);
  }

  pasteEvent(event) {
    event.preventDefault();
    const text = event.clipboardData.getData('Text');
    this.myControl.setValue('', {emitEvent: false});
    text.split(';').forEach(newAllele => {
      this._alleles.push({alleleSymbol: newAllele});
    });
  }
}
