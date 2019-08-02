import { AuthService } from './../../shared/services/auth.service';
import { FilterService } from './../../shared/services/filter.service';
import { Component, OnInit } from '@angular/core';
import { PublicationService } from 'src/app/shared/services/publication.service';
import { environment } from 'src/environments/environment';
import { startWith, map, switchMap } from 'rxjs/operators';
import { merge } from 'rxjs';

@Component({
  selector: 'impc-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  pendingNumber = 0;
  falsePositiveNumber = 0;
  reviewedNumber = 0;
  categories = [];
  showActions = false;

  constructor(
    private publicationService: PublicationService,
    private filterService: FilterService,
    private authService: AuthService
  ) {
    this.showActions = this.authService.isLoggedIn();
    this.categories = environment.categories;
    this.categories.forEach(category =>
      merge(
        this.filterService.filterChange,
        this.publicationService.reloadPublications
      )
        .pipe(
          startWith({}),
          switchMap(() => this.getCount({ ...category.filter, ...this.filterService.filter }))
        )
        .subscribe(count => {
          category.count = count;
        })
    );
    this.authService.loggedOut.subscribe(() => this.showActions = false );
  }

  ngOnInit() {}

  getCount(filter) {
    return this.publicationService.getPublicationsNumber(filter);
  }
}
