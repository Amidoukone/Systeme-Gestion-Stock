import {DataChartService} from './../../services/data-chart.service';
import Chart from 'chart.js/auto';
import {ChartModule} from 'primeng/chart';
import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import * as XLSX from 'xlsx';
import {saveAs} from 'file-saver';
import {DashboardService} from "../../services/dashboard.service";
import { BonEntreeService } from '../../services/bon-entree.service';
import { BonEntree } from '../../models/bon-entree';
import { BonSortieService } from '../../services/bon-sortie.service';
import { BonSortie } from '../../models/bon-sortie';
import {NgxDatatableModule} from "@swimlane/ngx-datatable";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ChartModule, NgxDatatableModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  title = 'Dashboard';
  public chart: any;

  data: any;
  data2: any;
  options2: any;
  data3: any;
  options3: any;
  totalEntrees: number = 0;
  totalSorties: number = 0;
  salesByCategory: any[] = [];

  stockTotal: number = 0;
  valeurStock: number = 0;
  valeurEntrees: number = 0;
  valeurSorties: number = 0;

  bonEntrees: BonEntree[] = [];
  bonSorties: BonSortie[] = [];

  bonEntreeColumns = [
    { prop: 'id', name: 'ID' },
    { prop: 'dateCommande', name: 'Date de Commande' },
    { prop: 'fournisseur.name', name: 'Fournisseur' },
    // Ajoutez d'autres colonnes nécessaires ici
  ];

  bonSortieColumns = [
    { prop: 'id', name: 'ID' },
    { prop: 'dateSortie', name: 'Date de Sortie' },
    { prop: 'utilisateur.username', name: 'Utilisateur' },
    // Ajoutez d'autres colonnes nécessaires ici
  ];

  constructor(
    private dataChartService: DataChartService,
    private dashboardService: DashboardService,
    private bonEntreeService: BonEntreeService,
    private bonSortieService: BonSortieService
    ) {}

  ngOnInit() {
    this.getTrends();
    this.getStats();
    this.getSalesByCategory();
    this.loadStockInfo();
    this.bonEntreeService.getBonEntrees().subscribe(data => {
      this.bonEntrees = data;
    });
    this.bonSortieService.getBonSorties().subscribe(data => {
      this.bonSorties = data;
    });
  }

  getTrends() {
    this.dataChartService.getTrends().subscribe(
      response => {
        this.data = response;
        this.createChart();
      },
      error => {
        console.error('Erreur lors de la récupération des tendances', error);
      }
    );
  }

  getStats() {
    this.dataChartService.getStats().subscribe(
      response => {
        this.totalEntrees = response.totalEntrees;
        this.totalSorties = response.totalSorties;
      },
      error => {
        console.error('Erreur lors de la récupération des statistiques', error);
      }
    );
  }

  getSalesByCategory() {
    this.dataChartService.getSalesByCategory().subscribe(
      response => {
        this.salesByCategory = response;
        this.createSalesByCategoryChart();
      },
      error => {
        console.error('Erreur lors de la récupération des ventes par catégories', error);
      }
    );
  }

  createChart() {
    // Votre logique pour initialiser le graphique avec this.data
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    this.data = {
      labels: this.data.entrees.map((e: [number, number]) => `Mois ${e[0]}`),
      datasets: [
        {
          label: 'Entrées',
          borderColor: documentStyle.getPropertyValue('--blue-500'),
          backgroundColor: documentStyle.getPropertyValue('--blue-500'),
          data: this.data.entrees.map((e: [number, number]) => e[1])
        },
        {
          label: 'Sorties',
          borderColor: documentStyle.getPropertyValue('--pink-500'),
          backgroundColor: documentStyle.getPropertyValue('--pink-500'),
          data: this.data.sorties.map((s: [number, number]) => s[1])
        }
      ]
    };

    // Initialiser le chart ici si nécessaire
  }

  Chart2() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    this.data2 = {
      labels: ['Janvier', 'Fevrier', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet'],
      datasets: [
        {
          label: 'Bons Entrées',
          backgroundColor: documentStyle.getPropertyValue('--blue-500'),
          borderColor: documentStyle.getPropertyValue('--blue-500'),
          data: [65, 59, 80, 81, 56, 55, 40]
        },
        {
          label: 'Bons Sorties',
          backgroundColor: documentStyle.getPropertyValue('--pink-500'),
          borderColor: documentStyle.getPropertyValue('--pink-500'),
          data: [28, 48, 40, 19, 86, 27, 90]
        }
      ]
    };

    this.options2 = {
      maintainAspectRatio: false,
      aspectRatio: 0.8,
      plugins: {
        legend: {
          labels: {
            color: textColor
          }
        }
      },
      scales: {
        x: {
          ticks: {
            color: textColorSecondary,
            font: {
              weight: 500
            }
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false
          }
        },
        y: {
          ticks: {
            color: textColorSecondary
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false
          }
        }
      }
    };
  }

  Chart3() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    this.data3 = {
      labels: ['Janvier', 'Fevrier', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet'],
      datasets: [
        {
          label: 'Bons Entrées',
          backgroundColor: documentStyle.getPropertyValue('--blue-500'),
          borderColor: documentStyle.getPropertyValue('--blue-500'),
          data: [65, 59, 80, 81, 56, 55, 40]
        },
        {
          label: 'Bons Sorties',
          backgroundColor: documentStyle.getPropertyValue('--pink-500'),
          borderColor: documentStyle.getPropertyValue('--pink-500'),
          data: [28, 48, 40, 19, 86, 27, 90]
        }
      ]
    };

    this.options3 = {
      maintainAspectRatio: false,
      aspectRatio: 0.8,
      plugins: {
        legend: {
          labels: {
            color: textColor
          }
        }
      },
      scales: {
        x: {
          ticks: {
            color: textColorSecondary,
            font: {
              weight: 500
            }
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false
          }
        },
        y: {
          ticks: {
            color: textColorSecondary
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false
          }
        }
      }
    };
  }

  createSalesByCategoryChart() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    this.data3 = {
      labels: this.salesByCategory.map(category => category[0]),
      datasets: [
        {
          label: 'Ventes par Catégories',
          backgroundColor: documentStyle.getPropertyValue('--blue-500'),
          borderColor: documentStyle.getPropertyValue('--blue-500'),
          data: this.salesByCategory.map(category => category[1])
        }
      ]
    };

    this.options3 = {
      maintainAspectRatio: false,
      aspectRatio: 0.8,
      plugins: {
        legend: {
          labels: {
            color: textColor
          }
        }
      },
      scales: {
        x: {
          ticks: {
            color: textColorSecondary,
            font: {
              weight: 500
            }
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false
          }
        },
        y: {
          ticks: {
            color: textColorSecondary
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false
          }
        }
      }
    };
  }

  loadStockInfo(): void {
    this.dashboardService.getStockInfo().subscribe((data: any) => {
      this.stockTotal = data.stockTotal;
      this.totalEntrees = data.totalEntrees;
      this.valeurEntrees = data.valeurEntrees;
      this.totalSorties = data.totalSorties;
      this.valeurSorties = data.valeurSorties;
    });
  }

  exportToExcel(filename: string, data: any[]): void {
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
    const workbook: XLSX.WorkBook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };
    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    this.saveAsExcelFile(excelBuffer, filename);
  }

  private saveAsExcelFile(buffer: any, fileName: string): void {
    const data: Blob = new Blob([buffer], {type: EXCEL_TYPE});
    saveAs(data, `${fileName}_export_${new Date().getTime()}.xlsx`);
  }
}

const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
