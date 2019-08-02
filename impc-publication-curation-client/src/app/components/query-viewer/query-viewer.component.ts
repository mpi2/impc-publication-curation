import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'impc-query-viewer',
  templateUrl: './query-viewer.component.html',
  styleUrls: ['./query-viewer.component.scss']
})
export class QueryViewerComponent implements OnInit {

  @Input()
  filters = [];

  constructor() { }

  ngOnInit() {
  }

}
