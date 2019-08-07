import { FilterService } from './../../shared/services/filter.service';
import { ngxCsv } from 'ngx-csv/ngx-csv';
import { Publication } from '../../shared/models/publication.model';
import {
  AfterViewInit,
  Component,
  OnInit,
  ViewChild,
  Input
} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, SortDirection } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { PublicationService } from 'src/app/shared/services/publication.service';
import { merge, of as observableOf } from 'rxjs';
import { startWith, switchMap, map, catchError, filter } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'impc-publication-table',
  templateUrl: './publication-table.component.html',
  styleUrls: ['./publication-table.component.scss']
})
export class PublicationTableComponent implements AfterViewInit, OnInit {
  @ViewChild('paginatorTop', { static: false }) paginatorTop: MatPaginator;
  @ViewChild('paginatorBottom', { static: false })
  paginatorBottom: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;
  @ViewChild(MatTable, { static: false }) table: MatTable<Publication>;
  sortDirection = 'DESC';
  sortable = [
    { value: 'firstPublicationDate', viewValue: 'Publication date' },
    { value: 'title', viewValue: 'Title' },
    { value: 'authorList', viewValue: 'First author' },
    { value: 'keyword', viewValue: 'Matched keyword' },
    { value: 'journal', viewValue: 'Journal' }
  ];
  sortActive = this.sortable[0].value;

  isLoadingResults = true;
  notFound = false;

  @Input()
  showActions = false;

  @Input()
  resultsLength = 0;

  @Input()
  status;

  publications: Publication[] = [];

  constructor(
    private publicationService: PublicationService,
    private filterService: FilterService
  ) {}

  ngOnInit() {}

  ngAfterViewInit() {
    merge(this.sort.sortChange, this.filterService.filterChange).subscribe(
      () => {
        this.paginatorTop.pageIndex = 0;
        this.paginatorBottom.pageIndex = 0;
      }
    );

    merge(
      this.sort.sortChange,
      this.paginatorTop.page,
      this.paginatorBottom.page,
      this.filterService.filterChange,
      this.publicationService.reloadPublications
    )
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.publicationService.getPublications(
            this.paginatorTop.pageIndex,
            this.paginatorTop.pageSize,
            { status: this.status, ...this.filterService.filter },
            this.sortActive,
            this.sortDirection
          );
        }),
        map(result => {
          this.isLoadingResults = false;
          return result;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          return observableOf([]);
        })
      )
      .subscribe(data => {
        this.publications = data;
        this.notFound = this.publications.length === 0;
      });
  }

  syncPaginator(event, paginator, scrolltop) {
    paginator.pageIndex = event.pageIndex;
    paginator.pageSize = event.pageSize;
    if (scrolltop) {
      this.scrollToTop();
    }
  }

  scrollToTop = () => {
    const c = document.documentElement.scrollTop || document.body.scrollTop;
    if (c > 0) {
      window.requestAnimationFrame(this.scrollToTop);
      window.scrollTo(0, c - c / 8);
    }
  };

  changeDirection() {
    this.sortDirection = this.sortDirection === 'ASC' ? 'DESC' : 'ASC';
    this.changeSort();
  }

  changeSort() {
    this.sort.sortChange.emit({
      active: this.sortActive,
      direction: this.sortDirection as SortDirection
    });
  }

  downloadCsv() {
    this.publicationService
      .getPublications(0, this.resultsLength, {
        status,
        ...this.filterService.filter
      })
      .subscribe(publications => {
        const csvPublications = publications.map(publication => {
          const csvPublication: any = {};
          csvPublication.orderId = publication.orderId
            ? publication.orderId
            : '';
          csvPublication.pmid = publication.pmid;
          csvPublication.title = publication.title;
          csvPublication.alleles = publication.alleles
            .map(allele => allele.alleleSymbol)
            .join('; ');
          return csvPublication;
        });
        const csv = new ngxCsv(csvPublications, `${environment.title} report`, {
          showLabels: true,
          headers: ['Order ID', 'pmid', 'title', 'alleles']
        });
      });
  }
}
