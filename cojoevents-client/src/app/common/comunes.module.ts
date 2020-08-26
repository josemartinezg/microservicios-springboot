import { NgModule, ErrorHandler } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { AppErrorHandler } from './app-error-handler';
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  imports: [
    CommonModule,
  ],
  declarations: [],
  providers: [
    {provide: ErrorHandler, useClass: AppErrorHandler}
  ]
})
export class ComunesModule { }
