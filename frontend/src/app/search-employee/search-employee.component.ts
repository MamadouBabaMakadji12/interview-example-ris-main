import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnDestroy, Output } from '@angular/core';
import { debounceTime, Subject, Subscription } from 'rxjs';

@Component({
  selector: 'search-employee',
  standalone: true,
  templateUrl: './search-employee.component.html',
  styleUrl: './search-employee.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchEmployeeComponent implements OnDestroy {
  @Input()
  title!: string;
  @Output()
  onSearch: EventEmitter<string> = new EventEmitter<string>();
  keywordSubject = new Subject<string>();
  subscription: Subscription = new Subscription();

  constructor() {
    this.subscription = this.keywordSubject
      .pipe(debounceTime(500))
      .subscribe((value) => {
        this.onSearch.emit(value);
      });
  }

  onInputChange(event: any): void {
    const value = event.target.value;
    this.keywordSubject.next(value);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
