import { environment } from './../../../environments/environment';
import { PublicationService } from './../../shared/services/publication.service';
import {
  Publication,
  Correspondence
} from './../../shared/models/publication.model';
import { Component, OnInit, Input } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { of as observableOf } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Component({
  selector: 'impc-publication-card',
  templateUrl: './publication-card.component.html',
  styleUrls: ['./publication-card.component.scss']
})
export class PublicationCardComponent implements OnInit {
  @Input()
  publication: Publication;

  @Input()
  showActions = false;

  showConsortiumCheck = true;
  showEmailConfirm = false;
  showOrderId = false;

  constructor(
    private publicationService: PublicationService,
    private snackBar: MatSnackBar
  ) {
    this.showConsortiumCheck = environment.consortiumPaperMarker;
    this.showEmailConfirm = environment.confirmViaEmail;
    this.showOrderId = environment.showOrderID;
  }

  ngOnInit() {}

  setStatus(reviewed, falsePositive, waitingForEmail = false, status = '', undoing = false) {
    this.publicationService
      .setPublicationStatus(
        this.publication.pmid,
        reviewed,
        this.publication.alleles,
        falsePositive,
        this.publication.consortiumPaper,
        waitingForEmail,
        this.publication.orderId
      )
      .pipe(
        map(() => observableOf(true)),
        catchError(() => observableOf(false))
      )
      .subscribe(done => {
        if (done) {
          this.openSnackBar(
            `${undoing ? 'un' : ''}${status === 'updated' ? '' : 'marked as '}${status}`,
            status === 'updated' || undoing,
            status
          );
        } else {
          this.openSnackBar(
            'there was an error processing your request',
            true,
            status
          );
        }
      });
  }

  getMailTo(correspondence: Correspondence) {
    const emails = correspondence.emails.join(',');
    return `mailto:${emails}?subject=INFRAFRONTIER resource usage confirmation`;
  }

  openSnackBar(message, hideAction, status) {
    const action = hideAction ? undefined : 'UNDO';
    message = message.charAt(0).toUpperCase() + message.slice(1)
    const snackBarRef = this.snackBar.open(message, action, {
      duration: 2000
    });
    snackBarRef.onAction().subscribe(_ => {
      if (!hideAction) {
        this.setStatus(this.publication.reviewed, this.publication.falsePositive, this.publication.pendingEmailConfirmation, status, true);
      }
    });
  }
}
