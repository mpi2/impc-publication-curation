import { FilterService } from './../../shared/services/filter.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'impc-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {
  @Input()
  filters = [];

  filterForm: FormGroup;

  constructor(
    private httpClient: HttpClient,
    private filterService: FilterService
  ) {

  }

  ngOnInit() {
    const controls = {
      search: new FormControl('')
    };
    this.filters.forEach(filter => {
      if (filter.type === 'array') {
        controls[filter.field] = new FormControl([]);
      } else if (filter.type === 'range') {
        controls[filter.field + 'From'] = new FormControl(filter.values[0]);
        controls[filter.field + 'To'] = new FormControl(filter.values[1]);
      } else if (filter.type === 'boolean') {
        controls[filter.field] = new FormControl([]);
      }
    });
    this.filterForm = new FormGroup(controls);
    this.filters = this.filters.map(filter => {
      if (filter.url !== undefined) {
        filter.values = this.httpClient.get(filter.url);
      }
      return filter;
    });
    this.filterForm.valueChanges.subscribe(value => this.filterService.emitFilterChange(value));
    this.filterService.searchChange.subscribe(value => this.filterForm.controls.search.setValue(value));
  }
}
