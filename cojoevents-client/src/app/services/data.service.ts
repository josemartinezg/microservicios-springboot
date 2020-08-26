import { Injectable } from '@angular/core';
import { BadInput } from '../common/bad-input';
import { AppError } from '../common/app-error';
import { NotFoundError } from '../common/not-found-error';
import { Headers, RequestOptions, Http } from '@angular/http';
import { catchError, map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(public url : string, protected http : Http) { }

  create(resource){
    console.log(resource);
    const headers = new Headers({ 'Accept': 'application/json', 'Content-Type': 'application/json'});
    const options = new RequestOptions({headers : headers});
    return this.http.post(this.url + 'realizar-venta', JSON.stringify(resource), options)
    .pipe(map(response => response.json()), catchError(this.handlerError));
  }
  public handlerError(error : Response){
    if (error.status === 400) {
      return Observable.throw(new BadInput(error.json()));
    }
    if (error.status === 404) {
      return Observable.throw(new NotFoundError());
    }
    return Observable.throw(new AppError(error));
  }
}


